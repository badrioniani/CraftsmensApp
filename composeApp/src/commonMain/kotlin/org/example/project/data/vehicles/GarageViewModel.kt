package org.example.project.data.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.UserVehicle
import org.example.project.data.catalog.CarBrandDto
import org.example.project.data.catalog.CatalogApi

data class GarageState(
    val loading: Boolean = false,
    val vehicles: List<UserVehicle> = emptyList(),
    val brands: List<CarBrandDto> = emptyList(),
    val error: String? = null,
)

private fun VehicleDto.toUiModel(): UserVehicle = UserVehicle(
    id = id.toString(),
    brandId = brand.toString(),
    modelName = modelName,
    year = year,
    nickname = nickname,
    brandName = brandName,
)

class GarageViewModel(
    private val api: VehicleApi = VehicleApi(),
    private val catalog: CatalogApi = CatalogApi(),
) : ViewModel() {

    private val _state = MutableStateFlow(GarageState())
    val state: StateFlow<GarageState> = _state.asStateFlow()

    private var loaded = false

    fun load() {
        if (loaded) return
        loaded = true
        _state.value = _state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            val brands = runCatching { catalog.brands() }.getOrDefault(emptyList())
            runCatching { api.list() }
                .onSuccess { list ->
                    _state.value = GarageState(
                        loading = false,
                        vehicles = list.map { it.toUiModel() },
                        brands = brands,
                    )
                }
                .onFailure {
                    _state.value = GarageState(
                        loading = false,
                        brands = brands,
                        error = it.message ?: "Could not load your garage",
                    )
                }
        }
    }

    fun add(brandId: Int, model: String?, year: Int?, nickname: String) {
        viewModelScope.launch {
            runCatching { api.create(VehicleInput(brand = brandId, year = year, nickname = nickname)) }
                .onSuccess { dto ->
                    // The create endpoint takes a model PK; we only have free text,
                    // so surface the typed model name locally for display.
                    val vehicle = dto.toUiModel().copy(modelName = model ?: dto.modelName)
                    _state.value = _state.value.copy(vehicles = _state.value.vehicles + vehicle)
                }
                .onFailure { _state.value = _state.value.copy(error = it.message ?: "Could not save vehicle") }
        }
    }

    fun remove(vehicle: UserVehicle) {
        val id = vehicle.id.toIntOrNull() ?: return
        // Optimistic removal.
        _state.value = _state.value.copy(vehicles = _state.value.vehicles.filterNot { it.id == vehicle.id })
        viewModelScope.launch {
            runCatching { api.delete(id) }
                .onFailure { _state.value = _state.value.copy(error = it.message ?: "Could not delete vehicle") }
        }
    }
}
