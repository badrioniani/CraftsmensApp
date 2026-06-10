package org.example.project.data.mechanics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.catalog.CarBrandDto
import org.example.project.data.catalog.CarModelDto
import org.example.project.data.catalog.CatalogApi
import org.example.project.data.catalog.CityDto
import org.example.project.data.catalog.ServiceTypeDto

/** One editable specialization row, grouped by brand like the web dashboard. */
data class SpecRow(
    val brands: List<Int> = emptyList(),
    val models: List<Int> = emptyList(),
    val serviceTypes: List<Int> = emptyList(),
    val allBrands: Boolean = false,
    val allServices: Boolean = false,
)

data class DashboardForm(
    val businessName: String = "",
    val description: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val city: String = "",
    val district: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val specializations: List<SpecRow> = emptyList(),
)

data class DashboardState(
    val loading: Boolean = true,
    val saving: Boolean = false,
    val editing: Boolean = false,
    val error: String? = null,
    val notice: String? = null,
    val savedProfile: MechanicDto? = null,
    val profileId: Int? = null,
    val form: DashboardForm = DashboardForm(),
    val dirty: Boolean = false,
    val brands: List<CarBrandDto> = emptyList(),
    val models: List<CarModelDto> = emptyList(),
    val services: List<ServiceTypeDto> = emptyList(),
    val cities: List<CityDto> = emptyList(),
    val reviews: List<ReviewDto> = emptyList(),
) {
    val completeness: Int get() = completenessOf(form)
    val specsCount: Int get() = form.specializations.sumOf { if (it.allServices) 1 else it.serviceTypes.size }
    val isVerified: Boolean get() = savedProfile?.isVerified == true
}

private const val ALL_BRANDS_KEY = -1

class MechanicDashboardViewModel(
    private val api: MechanicApi = MechanicApi(),
    private val catalog: CatalogApi = CatalogApi(),
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private var savedSnapshot = snapshot(DashboardForm())
    private var loaded = false

    fun load() {
        if (loaded) return
        loaded = true
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            val brands = runCatching { catalog.brands() }.getOrDefault(emptyList())
            val models = runCatching { catalog.models() }.getOrDefault(emptyList())
            val services = runCatching { catalog.services() }.getOrDefault(emptyList())
            val cities = runCatching { catalog.cities() }.getOrDefault(emptyList())

            val profileResult = runCatching { api.mine() }
            val profile = profileResult.getOrNull()
            val reviews = if (profile != null) {
                runCatching { api.reviews(profile.id).results }.getOrDefault(emptyList())
            } else emptyList()

            // A 404 from mine() simply means "no profile yet" — not an error.
            val failure = profileResult.exceptionOrNull()
            val realError = failure?.takeIf { (it as? MechanicApiException)?.httpStatus != 404 }

            val form = profile?.let { fromProfile(it) } ?: DashboardForm()
            savedSnapshot = snapshot(form)
            _state.value = _state.value.copy(
                loading = false,
                savedProfile = profile,
                profileId = profile?.id,
                form = form,
                dirty = false,
                brands = brands,
                models = models,
                services = services,
                cities = cities,
                reviews = reviews,
                error = realError?.message,
                // Open the editor straight away when there's no profile to view.
                editing = profile == null && realError == null,
            )
        }
    }

    fun startEditing() {
        _state.value = _state.value.copy(editing = true, error = null, notice = null)
    }

    fun cancelEditing() {
        val saved = _state.value.savedProfile
        val form = saved?.let { fromProfile(it) } ?: DashboardForm()
        savedSnapshot = snapshot(form)
        _state.value = _state.value.copy(editing = false, form = form, dirty = false, error = null, notice = null)
    }

    private fun updateForm(transform: (DashboardForm) -> DashboardForm) {
        val next = transform(_state.value.form)
        _state.value = _state.value.copy(form = next, dirty = snapshot(next) != savedSnapshot, notice = null)
    }

    fun setBusinessName(v: String) = updateForm { it.copy(businessName = v) }
    fun setDescription(v: String) = updateForm { it.copy(description = v) }
    fun setPhone(v: String) = updateForm { it.copy(phone = v) }
    fun setWhatsapp(v: String) = updateForm { it.copy(whatsapp = v) }
    fun setAddress(v: String) = updateForm { it.copy(address = v) }

    fun setCity(v: String) = updateForm {
        // Changing city resets the district and (if no street address) the pin.
        it.copy(city = v, district = "", latitude = if (it.address.isBlank()) "" else it.latitude, longitude = if (it.address.isBlank()) "" else it.longitude)
    }

    fun setDistrict(v: String) = updateForm { it.copy(district = v) }

    fun setPin(lat: Double, lng: Double) = updateForm {
        it.copy(latitude = lat.toFixed6(), longitude = lng.toFixed6())
    }

    fun clearPin() = updateForm { it.copy(latitude = "", longitude = "") }

    /** Drop the pin at the device location and reverse-geocode address + city + district. */
    fun useMyLocation(lat: Double, lng: Double) {
        setPin(lat, lng)
        viewModelScope.launch {
            val geo = org.example.project.data.network.Geocoder.reverse(lat, lng) ?: return@launch
            updateForm { form ->
                val cities = _state.value.cities
                // Match the geocoded city against the catalog (try Georgian, then English).
                val matchedCity = geo.city?.let { name ->
                    cities.firstOrNull { c ->
                        c.name.equals(name, ignoreCase = true) ||
                            c.nameEn.equals(name, ignoreCase = true)
                    }
                }
                val nextCity = matchedCity?.name ?: form.city
                // District is meaningful only within the matched city's list — and only
                // if the catalog actually has a row that matches Nominatim's label.
                val nextDistrict = matchedCity?.districts?.firstOrNull { d ->
                    geo.district?.let {
                        d.name.equals(it, ignoreCase = true) ||
                            d.nameEn.equals(it, ignoreCase = true)
                    } == true
                }?.name ?: form.district
                form.copy(
                    address = geo.address.ifBlank { form.address },
                    city = nextCity,
                    district = nextDistrict,
                )
            }
        }
    }

    // ── Specialization editing ──
    fun addSpec() = updateForm { it.copy(specializations = it.specializations + SpecRow()) }

    fun removeSpec(index: Int) = updateForm {
        it.copy(specializations = it.specializations.filterIndexed { i, _ -> i != index })
    }

    private fun patchRow(index: Int, transform: (SpecRow) -> SpecRow) = updateForm {
        it.copy(specializations = it.specializations.mapIndexed { i, row -> if (i == index) transform(row) else row })
    }

    fun toggleBrand(index: Int, brandId: Int) = patchRow(index) { row ->
        val brands = if (row.brands.contains(brandId)) row.brands - brandId else row.brands + brandId
        // Models are brand-specific, so any brand change clears them.
        row.copy(brands = brands, models = emptyList())
    }

    fun toggleAllBrands(index: Int) = patchRow(index) { row ->
        if (row.allBrands) row.copy(allBrands = false)
        else row.copy(allBrands = true, brands = emptyList(), models = emptyList(), serviceTypes = emptyList(), allServices = false)
    }

    fun toggleModel(index: Int, modelId: Int) = patchRow(index) { row ->
        row.copy(models = if (row.models.contains(modelId)) row.models - modelId else row.models + modelId)
    }

    fun setAllModels(index: Int) = patchRow(index) { it.copy(models = emptyList()) }

    fun setAllServices(index: Int) = patchRow(index) { it.copy(serviceTypes = emptyList(), allServices = true) }

    fun toggleService(index: Int, serviceId: Int) = patchRow(index) { row ->
        row.copy(
            serviceTypes = if (row.serviceTypes.contains(serviceId)) row.serviceTypes - serviceId else row.serviceTypes + serviceId,
            allServices = false,
        )
    }

    fun save(
        onMissingBasics: String,
        onMissingCity: String,
        onIncompleteSpec: String,
        onSaveError: String,
        savedMessage: String,
    ) {
        val s = _state.value
        if (s.saving) return
        val form = s.form
        if (form.businessName.isBlank() || form.phone.isBlank()) {
            _state.value = s.copy(error = onMissingBasics)
            return
        }
        // Backend requires city — no blank=True on MechanicProfile.city.
        if (form.city.isBlank()) {
            _state.value = s.copy(error = onMissingCity)
            return
        }
        // Reject rows that picked a brand but no service (and vice versa).
        val incomplete = form.specializations.any { row ->
            val hasBrand = row.brands.isNotEmpty() || row.allBrands
            val hasService = row.serviceTypes.isNotEmpty() || row.allServices
            hasBrand != hasService
        }
        if (incomplete) {
            _state.value = s.copy(error = onIncompleteSpec)
            return
        }

        _state.value = s.copy(saving = true, error = null, notice = null)
        viewModelScope.launch {
            val req = MechanicUpsertRequest(
                businessName = form.businessName.trim(),
                description = form.description.trim(),
                phone = form.phone.trim(),
                whatsapp = form.whatsapp.trim(),
                city = form.city,
                district = form.district,
                address = form.address.trim(),
                latitude = form.latitude.ifBlank { null },
                longitude = form.longitude.ifBlank { null },
                specializations = flatSpecs(form.specializations),
            )
            val result = runCatching {
                val id = _state.value.profileId
                if (id != null) api.update(id, req) else api.create(req)
            }
            result.onSuccess { saved ->
                val next = fromProfile(saved)
                savedSnapshot = snapshot(next)
                val reviews = runCatching { api.reviews(saved.id).results }.getOrDefault(_state.value.reviews)
                _state.value = _state.value.copy(
                    saving = false,
                    editing = false,
                    savedProfile = saved,
                    profileId = saved.id,
                    form = next,
                    dirty = false,
                    reviews = reviews,
                    notice = savedMessage,
                    error = null,
                )
            }.onFailure {
                _state.value = _state.value.copy(saving = false, error = it.message ?: onSaveError)
            }
        }
    }

    // ── pure helpers ──
    private fun fromProfile(p: MechanicDto): DashboardForm {
        val rows = LinkedHashMap<Int, SpecRow>()
        for (spec in p.specializations) {
            val key = spec.brand ?: ALL_BRANDS_KEY
            val existing = rows[key] ?: SpecRow(
                brands = spec.brand?.let { listOf(it) } ?: emptyList(),
                allBrands = spec.brand == null,
            )
            var models = existing.models
            if (spec.model != null && !models.contains(spec.model)) models = models + spec.model
            var serviceTypes = existing.serviceTypes
            var allServices = existing.allServices
            if (spec.serviceType == null) allServices = true
            else if (!serviceTypes.contains(spec.serviceType)) serviceTypes = serviceTypes + spec.serviceType
            rows[key] = existing.copy(models = models, serviceTypes = serviceTypes, allServices = allServices)
        }
        return DashboardForm(
            businessName = p.businessName,
            description = p.description,
            phone = p.phone,
            whatsapp = p.whatsapp,
            city = p.city,
            district = p.district,
            address = p.address,
            latitude = p.latitude ?: "",
            longitude = p.longitude ?: "",
            specializations = rows.values.toList(),
        )
    }

    private fun flatSpecs(rows: List<SpecRow>): List<MechanicSpecializationWrite> {
        val complete = rows.filter { (it.brands.isNotEmpty() || it.allBrands) && (it.serviceTypes.isNotEmpty() || it.allServices) }
        return complete.flatMap { row ->
            val brandIds: List<Int?> = if (row.allBrands) listOf(null) else row.brands
            val modelIds: List<Int?> = if (row.models.isNotEmpty()) row.models else listOf(null)
            val serviceIds: List<Int?> = if (row.allServices) listOf(null) else row.serviceTypes
            brandIds.flatMap { b ->
                modelIds.flatMap { m ->
                    serviceIds.map { sv -> MechanicSpecializationWrite(brand = b, model = m, serviceType = sv) }
                }
            }
        }
    }
}

private fun Double.toFixed6(): String {
    val rounded = kotlin.math.round(this * 1_000_000.0) / 1_000_000.0
    return rounded.toString()
}

private fun snapshot(form: DashboardForm): String = buildString {
    append(form.businessName).append('|').append(form.description).append('|')
    append(form.phone).append('|').append(form.whatsapp).append('|')
    append(form.city).append('|').append(form.district).append('|').append(form.address).append('|')
    append(form.latitude).append('|').append(form.longitude).append('|')
    form.specializations.forEach { row ->
        append("[b:").append(row.brands.sorted().joinToString(","))
        append(";m:").append(row.models.sorted().joinToString(","))
        append(";s:").append(row.serviceTypes.sorted().joinToString(","))
        append(";ab:").append(row.allBrands).append(";as:").append(row.allServices).append(']')
    }
}

private fun completenessOf(form: DashboardForm): Int {
    var score = 0
    val total = 7
    if (form.businessName.trim().length >= 3) score++
    if (form.description.trim().length >= 30) score++
    if (form.phone.trim().length >= 5) score++
    if (form.whatsapp.trim().length >= 5) score++
    if (form.city.isNotBlank() && form.district.isNotBlank()) score++
    if (form.latitude.isNotBlank() && form.longitude.isNotBlank()) score++
    if (form.specializations.any { (it.brands.isNotEmpty() || it.allBrands) && (it.serviceTypes.isNotEmpty() || it.allServices) }) score++
    return ((score.toDouble() / total) * 100).toInt()
}
