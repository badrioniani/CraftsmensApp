package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.Mechanic
import org.example.project.data.auth.UserDto
import org.example.project.data.mechanics.DashboardForm
import org.example.project.data.mechanics.MechanicDashboardViewModel
import org.example.project.data.mechanics.SpecRow
import org.example.project.data.mechanics.toUiModel
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AppSpinner
import org.example.project.ui.components.AuthTextField
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.components.IconButton40
import org.example.project.ui.components.Pill
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconCheck
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.IconWrench
import org.example.project.ui.icons.IconX
import org.example.project.ui.map.MapCamera
import org.example.project.ui.map.MapPin
import org.example.project.ui.map.MapView
import org.example.project.ui.map.rememberCurrentLocation
import org.example.project.ui.theme.ACCENT_OPTIONS
import org.example.project.ui.theme.CraftsmenColors
import org.example.project.ui.theme.buildTheme

private val AMBER = Color(0xFFF59E0B)
private const val TBILISI_LAT = 41.7151
private const val TBILISI_LNG = 44.8271

@Composable
fun MechanicDashboardScreen(
    theme: CraftsmenColors,
    user: UserDto?,
    onBack: () -> Unit,
    onViewPublic: (Mechanic) -> Unit,
) {
    val s = LocalStrings.current
    // Key by user id so a different account (after logout + re-register) gets a
    // fresh view model instead of the previous user's cached profile.
    val vm: MechanicDashboardViewModel = viewModel(key = "mechDash_${user?.id ?: "anon"}") { MechanicDashboardViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.load() }

    if (user != null && user.role != "mechanic") {
        Column(
            modifier = Modifier.fillMaxSize().background(theme.bg).padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(s.dashNotMechanic, color = theme.textDim, fontSize = 14.sp)
        }
        return
    }

    val form = state.form
    val brandName = { id: Int -> state.brands.firstOrNull { it.id == id }?.name ?: "—" }
    val serviceName = { id: Int -> state.services.firstOrNull { it.id == id }?.name ?: "—" }
    val modelName = { id: Int -> state.models.firstOrNull { it.id == id }?.name ?: "—" }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        ScreenHeader(
            theme = theme,
            title = form.businessName.ifBlank { s.dashTitle },
            subtitle = user?.email,
            onBack = onBack,
            right = {
                if (!state.editing && state.savedProfile != null) {
                    IconButton40(theme = theme, onClick = vm::startEditing) {
                        IconWrench(size = 18.dp, color = theme.text, stroke = 2f)
                    }
                }
            },
        )

        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AppSpinner(size = 28.dp, color = theme.accent)
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            HeroCard(theme, state, onViewPublic)

            state.error?.let { err ->
                NoticeBar(theme, err, isError = true)
            }
            state.notice?.let { msg ->
                if (state.error == null) NoticeBar(theme, msg, isError = false)
            }

            if (state.editing) {
                EditSection(theme, vm, state)
            } else if (state.savedProfile != null) {
                ViewSection(theme, form, brandName, serviceName, modelName)
            } else {
                CreateProfileCard(theme, vm::startEditing)
            }

            CompletenessCard(theme, state.completeness)
            ReviewsCard(theme, state)

            Spacer(Modifier.height(24.dp))
        }
    }
}

/* ---------- Hero ---------- */

@Composable
private fun HeroCard(
    theme: CraftsmenColors,
    state: org.example.project.data.mechanics.DashboardState,
    onViewPublic: (Mechanic) -> Unit,
) {
    val s = LocalStrings.current
    val form = state.form
    val rating = state.savedProfile?.averageRating?.toDoubleOrNull()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(20.dp))
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)).background(theme.accentSoft),
                contentAlignment = Alignment.Center,
            ) {
                val ini = initialsOf(form.businessName)
                if (ini.isBlank()) IconWrench(size = 24.dp, color = theme.accent, stroke = 2f)
                else Text(ini, color = theme.accent, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                StatusBadge(theme, state)
                Spacer(Modifier.height(6.dp))
                if (form.city.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconPin(size = 11.dp, color = theme.textMute)
                        Text(
                            text = form.city + if (form.district.isNotBlank()) " · ${form.district}" else "",
                            color = theme.textDim,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(theme, s.dashStatRating, rating?.let { fmt1(it) } ?: "—", Modifier.weight(1f))
            StatCard(theme, s.dashStatReviews, (state.savedProfile?.reviewCount ?: 0).toString(), Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(theme, s.dashStatSpecs, state.specsCount.toString(), Modifier.weight(1f))
            val status = when {
                state.isVerified -> s.badgeVerified
                state.savedProfile != null -> s.dashAwaitingVerification
                else -> "—"
            }
            StatCard(theme, s.dashStatStatus, status, Modifier.weight(1f))
        }

        if (state.savedProfile != null && !state.editing) {
            Spacer(Modifier.height(14.dp))
            AppButton(
                theme = theme,
                text = s.dashViewPublic,
                onClick = { onViewPublic(state.savedProfile.toUiModel()) },
                variant = ButtonVariant.Secondary,
            )
        }
    }
}

@Composable
private fun StatusBadge(theme: CraftsmenColors, state: org.example.project.data.mechanics.DashboardState) {
    val s = LocalStrings.current
    val (label, color) = when {
        state.isVerified -> s.badgeVerified to theme.success
        state.savedProfile != null -> s.dashAwaitingVerification to AMBER
        else -> s.dashNoProfile to theme.textMute
    }
    Row(
        modifier = Modifier.clip(CircleShape).background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.4f), CircleShape).padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        IconShield(size = 11.dp, color = color)
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun StatCard(theme: CraftsmenColors, label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).background(theme.bgInput).padding(12.dp),
    ) {
        Text(label.uppercase(), color = theme.textMute, fontSize = 9.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.6.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = theme.text, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/* ---------- View mode ---------- */

@Composable
private fun ViewSection(
    theme: CraftsmenColors,
    form: DashboardForm,
    brandName: (Int) -> String,
    serviceName: (Int) -> String,
    modelName: (Int) -> String,
) {
    val s = LocalStrings.current

    SectionCard(theme, s.dashSectionBusiness) {
        if (form.phone.isNotBlank()) InfoRow(theme, { IconPhone(size = 14.dp, color = theme.accent) }, form.phone)
        if (form.whatsapp.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            InfoRow(theme, { IconPhone(size = 14.dp, color = theme.success) }, "WhatsApp · ${form.whatsapp}")
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = form.description.ifBlank { "—" },
            color = if (form.description.isBlank()) theme.textMute else theme.textDim,
            fontSize = 13.sp,
            lineHeight = 19.sp,
        )
    }

    if (form.city.isNotBlank() || form.address.isNotBlank()) {
        Spacer(Modifier.height(14.dp))
        SectionCard(theme, s.dashSectionLocation) {
            if (form.city.isNotBlank()) {
                Text(
                    text = form.city + if (form.district.isNotBlank()) " · ${form.district}" else "",
                    color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                )
            }
            if (form.address.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text(form.address, color = theme.textDim, fontSize = 13.sp)
            }
        }
    }

    Spacer(Modifier.height(14.dp))
    SectionCard(theme, s.dashSectionServices) {
        if (form.specializations.isEmpty()) {
            Text(s.dashSpecsEmpty, color = theme.textMute, fontSize = 13.sp, lineHeight = 19.sp)
        } else {
            form.specializations.forEachIndexed { i, row ->
                if (i > 0) Spacer(Modifier.height(10.dp))
                SpecRowView(theme, row, brandName, serviceName, modelName)
            }
        }
    }
}

@Composable
private fun SpecRowView(
    theme: CraftsmenColors,
    row: SpecRow,
    brandName: (Int) -> String,
    serviceName: (Int) -> String,
    modelName: (Int) -> String,
) {
    val s = LocalStrings.current
    val title = if (row.allBrands) s.dashAllBrands else row.brands.joinToString(" · ") { brandName(it) }
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .background(theme.bgInput).padding(12.dp),
    ) {
        Text(title.ifBlank { "—" }, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        if (row.models.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            ChipFlow(theme, row.models.map { modelName(it) }, accent = false)
        }
        Spacer(Modifier.height(6.dp))
        if (row.allServices) ChipFlow(theme, listOf(s.dashAllServices), accent = true)
        else if (row.serviceTypes.isNotEmpty()) ChipFlow(theme, row.serviceTypes.map { serviceName(it) }, accent = true)
    }
}

/* ---------- Edit mode ---------- */

@Composable
private fun EditSection(
    theme: CraftsmenColors,
    vm: MechanicDashboardViewModel,
    state: org.example.project.data.mechanics.DashboardState,
) {
    val s = LocalStrings.current
    val form = state.form
    val en = LocalLanguage.current.code == "en"

    // Business
    SectionCard(theme, s.dashSectionBusiness) {
        AuthTextField(theme, form.businessName, vm::setBusinessName, placeholder = s.dashBusinessName, label = s.dashBusinessName, imeAction = ImeAction.Next)
        Spacer(Modifier.height(12.dp))
        MultilineField(theme, s.dashDescription, form.description, s.dashDescriptionPlaceholder, vm::setDescription)
        Spacer(Modifier.height(12.dp))
        AuthTextField(theme, form.phone, vm::setPhone, placeholder = "555 12 34 56", label = s.dashPhone, leading = { IconPhone(size = 16.dp, color = theme.textDim) }, keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
        Spacer(Modifier.height(12.dp))
        AuthTextField(theme, form.whatsapp, vm::setWhatsapp, placeholder = "555 12 34 56", label = s.dashWhatsapp, leading = { IconPhone(size = 16.dp, color = theme.textDim) }, keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
    }

    Spacer(Modifier.height(14.dp))

    // Location
    SectionCard(theme, s.dashSectionLocation) {
        FieldLabelText(theme, s.dashCity)
        Spacer(Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(state.cities) { city ->
                val display = if (en && city.nameEn.isNotBlank()) city.nameEn else city.name
                Pill(theme, display, active = form.city == city.name, onClick = { vm.setCity(city.name) }, small = true)
            }
        }
        val districts = state.cities.firstOrNull { it.name == form.city }?.districts ?: emptyList()
        if (districts.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            FieldLabelText(theme, s.dashDistrict)
            Spacer(Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(districts) { d ->
                    val display = if (en && d.nameEn.isNotBlank()) d.nameEn else d.name
                    Pill(theme, display, active = form.district == d.name, onClick = { vm.setDistrict(d.name) }, small = true)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        AuthTextField(theme, form.address, vm::setAddress, placeholder = s.dashAddress, label = s.dashAddress, imeAction = ImeAction.Done)

        Spacer(Modifier.height(12.dp))
        val myLocation = rememberCurrentLocation()
        FieldLabelText(theme, s.dashLocationPin)
        Spacer(Modifier.height(8.dp))
        val lat = form.latitude.toDoubleOrNull()
        val lng = form.longitude.toDoubleOrNull()
        val camera = if (lat != null && lng != null) MapCamera(lat, lng, 14f) else MapCamera(TBILISI_LAT, TBILISI_LNG, 11f)
        val pins = if (lat != null && lng != null) listOf(MapPin("sel", lat, lng, form.businessName.ifBlank { "·" })) else emptyList()
        Box(modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(14.dp)).border(1.dp, theme.border, RoundedCornerShape(14.dp))) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                pins = pins,
                camera = camera,
                onMapTap = { la, ln -> vm.setPin(la, ln) },
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = if (lat != null && lng != null) "${fmt1(lat)}, ${fmt1(lng)}" else s.dashClickToPin,
                color = theme.textDim, fontSize = 12.sp, modifier = Modifier.weight(1f),
            )
            if (lat != null && lng != null) {
                Box(modifier = Modifier.clip(CircleShape).clickable { vm.clearPin() }.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(s.dashClearPin, color = theme.accent, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        AppButton(
            theme = theme,
            text = s.dashUseMyLocation,
            onClick = { myLocation?.let { vm.useMyLocation(it.lat, it.lng) } },
            enabled = myLocation != null,
            variant = ButtonVariant.Secondary,
            leadingIcon = { IconPin(size = 16.dp, color = theme.text) },
        )
        if (lat == null || lng == null) {
            Spacer(Modifier.height(4.dp))
            Text(s.dashLocationNotSet, color = AMBER, fontSize = 11.sp)
        }
    }

    Spacer(Modifier.height(14.dp))

    // Services / specializations
    SectionCard(theme, s.dashSectionServices, action = {
        Box(modifier = Modifier.clip(CircleShape).background(theme.accentSoft).clickable { vm.addSpec() }.padding(horizontal = 14.dp, vertical = 6.dp)) {
            Text("+", color = theme.accent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }) {
        if (form.specializations.isEmpty()) {
            Text(s.dashSpecsEmpty, color = theme.textMute, fontSize = 13.sp, lineHeight = 19.sp)
        } else {
            form.specializations.forEachIndexed { index, row ->
                if (index > 0) Spacer(Modifier.height(12.dp))
                SpecRowEditor(theme, vm, state, index, row)
            }
        }
    }

    Spacer(Modifier.height(16.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (state.savedProfile != null) {
            AppButton(theme, s.cancel, onClick = vm::cancelEditing, variant = ButtonVariant.Secondary, modifier = Modifier.weight(1f))
        }
        AppButton(
            theme = theme,
            text = if (state.saving) s.dashSaving else s.dashSaveProfile,
            onClick = { vm.save(s.dashSaveError, s.dashIncompleteSpec, s.dashSaveError, s.dashProfileSaved) },
            enabled = state.dirty && !state.saving,
            busy = state.saving,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SpecRowEditor(
    theme: CraftsmenColors,
    vm: MechanicDashboardViewModel,
    state: org.example.project.data.mechanics.DashboardState,
    index: Int,
    row: SpecRow,
) {
    val s = LocalStrings.current
    val hasBrand = row.brands.isNotEmpty() || row.allBrands
    val hasService = row.serviceTypes.isNotEmpty() || row.allServices
    val incomplete = hasBrand != hasService
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
            .background(theme.bgInput)
            .border(1.dp, if (incomplete) AMBER else theme.border, RoundedCornerShape(14.dp))
            .padding(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            FieldLabelText(theme, s.dashBrand)
            Spacer(Modifier.weight(1f))
            Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(theme.bgRaised).clickable { vm.removeSpec(index) }, contentAlignment = Alignment.Center) {
                IconX(size = 14.dp, color = theme.textDim)
            }
        }
        Spacer(Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            item { Pill(theme, s.dashAllBrands, active = row.allBrands, onClick = { vm.toggleAllBrands(index) }, small = true) }
            if (!row.allBrands) {
                items(state.brands) { b ->
                    Pill(theme, b.name, active = row.brands.contains(b.id), onClick = { vm.toggleBrand(index, b.id) }, small = true)
                }
            }
        }

        // Models — only when exactly one brand is selected.
        if (!row.allBrands && row.brands.size == 1) {
            val models = state.models.filter { it.brand == row.brands.first() }
            if (models.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                FieldLabelText(theme, s.dashAllModels)
                Spacer(Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    item { Pill(theme, s.dashAllModels, active = row.models.isEmpty(), onClick = { vm.setAllModels(index) }, small = true) }
                    items(models) { m ->
                        Pill(theme, m.name, active = row.models.contains(m.id), onClick = { vm.toggleModel(index, m.id) }, small = true)
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        FieldLabelText(theme, s.dashService)
        Spacer(Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            item { Pill(theme, s.dashAllServices, active = row.allServices, onClick = { vm.setAllServices(index) }, small = true) }
            items(state.services) { svc ->
                Pill(theme, svc.name, active = row.serviceTypes.contains(svc.id), onClick = { vm.toggleService(index, svc.id) }, small = true)
            }
        }
    }
}

/* ---------- Sidebar-style cards ---------- */

@Composable
private fun CreateProfileCard(theme: CraftsmenColors, onCreate: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(20.dp)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)).background(theme.accentSoft), contentAlignment = Alignment.Center) {
            IconWrench(size = 24.dp, color = theme.accent, stroke = 2f)
        }
        Spacer(Modifier.height(14.dp))
        Text(s.dashCreateProfileTitle, color = theme.text, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(4.dp))
        Text(s.dashCreateProfileBody, color = theme.textDim, fontSize = 13.sp, lineHeight = 19.sp)
        Spacer(Modifier.height(16.dp))
        AppButton(theme, s.dashEditProfile, onClick = onCreate)
    }
}

@Composable
private fun CompletenessCard(theme: CraftsmenColors, completeness: Int) {
    val s = LocalStrings.current
    val barColor = when {
        completeness >= 85 -> theme.success
        completeness >= 60 -> theme.accent
        else -> AMBER
    }
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp)).padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(s.dashCompleteness, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text("$completeness%", color = barColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(theme.bgInput)) {
            Box(modifier = Modifier.fillMaxWidth(completeness / 100f).height(8.dp).clip(CircleShape).background(barColor))
        }
    }
}

@Composable
private fun ReviewsCard(theme: CraftsmenColors, state: org.example.project.data.mechanics.DashboardState) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp)).padding(16.dp),
    ) {
        Text(s.dashRecentReviews, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))
        if (state.reviews.isEmpty()) {
            Text(s.dashReviewsAppearHere, color = theme.textMute, fontSize = 13.sp)
        } else {
            state.reviews.take(4).forEachIndexed { i, review ->
                if (i > 0) Spacer(Modifier.height(10.dp))
                Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(theme.bgInput).padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(review.user?.name ?: "User", color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                        IconStar(size = 12.dp, color = AMBER)
                        Text(review.rating.toString(), color = theme.textDim, fontSize = 12.sp)
                    }
                    if (review.comment.isNotBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(review.comment, color = theme.textDim, fontSize = 12.sp, lineHeight = 17.sp, maxLines = 3)
                    }
                }
            }
        }
    }
}

/* ---------- Small building blocks ---------- */

@Composable
private fun SectionCard(
    theme: CraftsmenColors,
    title: String,
    action: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(20.dp)).padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, color = theme.text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            if (action != null) action()
        }
        Spacer(Modifier.height(14.dp))
        content()
    }
}

@Composable
private fun InfoRow(theme: CraftsmenColors, icon: @Composable () -> Unit, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        icon()
        Text(value, color = theme.text, fontSize = 14.sp)
    }
}

@Composable
private fun NoticeBar(theme: CraftsmenColors, text: String, isError: Boolean) {
    val color = if (isError) theme.danger else theme.success
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(color.copy(alpha = 0.12f)).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (isError) IconX(size = 14.dp, color = color) else IconCheck(size = 14.dp, color = color, stroke = 2.5f)
        Text(text, color = color, fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun FieldLabelText(theme: CraftsmenColors, text: String) {
    Text(text.uppercase(), color = theme.textDim, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.8.sp)
}

@Composable
private fun ChipFlow(theme: CraftsmenColors, labels: List<String>, accent: Boolean) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(labels) { label ->
            Box(
                modifier = Modifier.clip(CircleShape)
                    .background(if (accent) theme.accentSoft else theme.bgRaised)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(label, color = if (accent) theme.accent else theme.textDim, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun MultilineField(
    theme: CraftsmenColors,
    label: String,
    value: String,
    placeholder: String,
    onChange: (String) -> Unit,
) {
    val setInputFocused = LocalSetInputFocused.current
    Column(modifier = Modifier.fillMaxWidth()) {
        FieldLabelText(theme, label)
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(110.dp).clip(RoundedCornerShape(14.dp))
                .background(theme.bgInput).border(1.dp, theme.border, RoundedCornerShape(14.dp)).padding(horizontal = 6.dp),
        ) {
            TextField(
                value = value,
                onValueChange = onChange,
                placeholder = { Text(placeholder, color = theme.textMute, fontSize = 14.sp, lineHeight = 19.sp) },
                modifier = Modifier.fillMaxSize().onFocusChanged { setInputFocused(it.isFocused) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Default),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = theme.accent,
                    focusedTextColor = theme.text,
                    unfocusedTextColor = theme.text,
                ),
            )
        }
    }
}

/* ---------- pure helpers ---------- */

private fun initialsOf(name: String): String =
    name.trim().split(" ").filter { it.isNotBlank() }.take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }.joinToString("")

private fun fmt1(v: Double): String {
    val r = kotlin.math.round(v * 10.0) / 10.0
    val whole = r.toInt()
    val dec = kotlin.math.round((r - whole) * 10).toInt()
    return "$whole.$dec"
}

