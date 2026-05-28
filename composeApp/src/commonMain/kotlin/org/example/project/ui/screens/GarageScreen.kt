package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.example.project.data.UserVehicle
import org.example.project.data.auth.UserDto
import org.example.project.data.catalog.CarBrandDto
import org.example.project.data.vehicles.GarageViewModel
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.components.Pill
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconCalendar
import org.example.project.ui.icons.IconLock
import org.example.project.ui.icons.IconX
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun GarageScreen(
    theme: CraftsmenColors,
    user: UserDto?,
    onSignIn: (() -> Unit)? = null,
) {
    val s = LocalStrings.current
    if (user == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.bg)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(theme.accentSoft),
                contentAlignment = Alignment.Center,
            ) { IconLock(size = 22.dp, color = theme.accent) }
            Spacer(Modifier.height(16.dp))
            Text(s.garageLoginRequired, color = theme.text, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            if (onSignIn != null) {
                Spacer(Modifier.height(20.dp))
                AppButton(theme = theme, text = s.loginCta, onClick = onSignIn)
            }
        }
        return
    }

    val vm: GarageViewModel = viewModel(key = "garage_${user.id}") { GarageViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.load() }
    var addOpen by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(theme.bg),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item { GarageHeader(theme) }
        item {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp)) {
                AppButton(
                    theme = theme,
                    text = s.garageAdd,
                    onClick = { addOpen = true },
                )
            }
        }
        if (state.vehicles.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 40.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(theme.bgCard)
                        .border(1.dp, theme.border, RoundedCornerShape(18.dp))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(theme.accentSoft),
                        contentAlignment = Alignment.Center,
                    ) { IconCalendar(size = 22.dp, color = theme.accent) }
                    Spacer(Modifier.height(12.dp))
                    Text(s.garageEmptyTitle, color = theme.text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        s.garageEmptyBody,
                        color = theme.textDim,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                    )
                }
            }
        } else {
            items(state.vehicles) { v ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    VehicleCard(theme, v, onDelete = { vm.remove(v) })
                }
            }
        }
    }

    if (addOpen) {
        AddVehicleSheet(
            theme = theme,
            brands = state.brands,
            onClose = { addOpen = false },
            onSave = { brandId, model, year, nickname ->
                vm.add(brandId, model, year, nickname)
                addOpen = false
            },
        )
    }
}

@Composable
private fun GarageHeader(theme: CraftsmenColors) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggle = LocalToggleLanguage.current
    Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(theme.accentSoft),
                contentAlignment = Alignment.Center,
            ) { IconCalendar(size = 18.dp, color = theme.accent) }
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, CircleShape)
                    .clickable { toggle() }
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(lang.display, color = theme.text, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Box(modifier = Modifier.size(width = 1.dp, height = 12.dp).background(theme.border))
                Text(if (lang.code == "en") "KA" else "EN", color = theme.textMute, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(s.garageTitle, color = theme.text, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp)
        Spacer(Modifier.height(4.dp))
        Text(s.garageSubtitle, color = theme.textDim, fontSize = 14.sp)
    }
}

@Composable
private fun VehicleCard(theme: CraftsmenColors, v: UserVehicle, onDelete: () -> Unit) {
    val brandLabel = v.brandName
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(theme.accentSoft),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = brandLabel.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                color = theme.accent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = listOfNotNull(brandLabel.ifBlank { null }, v.modelName, v.year?.toString()).joinToString(" · "),
                color = theme.text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
            if (v.nickname.isNotBlank()) {
                Text(text = v.nickname, color = theme.textDim, fontSize = 12.sp)
            }
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(theme.bgInput)
                .clickable(onClick = onDelete),
            contentAlignment = Alignment.Center,
        ) { IconX(size = 16.dp, color = theme.textDim) }
    }
}

@Composable
private fun AddVehicleSheet(
    theme: CraftsmenColors,
    brands: List<CarBrandDto>,
    onClose: () -> Unit,
    onSave: (brandId: Int, model: String?, year: Int?, nickname: String) -> Unit,
) {
    val s = LocalStrings.current
    var brandId by remember { mutableStateOf<Int?>(null) }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(theme.bgRaised)
                .clickable(enabled = false, onClick = {})
                .padding(20.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(theme.borderStrong)
                    .align(Alignment.CenterHorizontally),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(s.garageAdd, color = theme.text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Box(modifier = Modifier.clickable { onClose() }.padding(4.dp)) {
                    IconX(size = 20.dp, color = theme.textDim)
                }
            }
            Spacer(Modifier.height(14.dp))

            Text(s.garageBrand, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(brands) { b ->
                    Pill(theme, b.name, active = brandId == b.id, onClick = { brandId = b.id }, small = true)
                }
            }
            Spacer(Modifier.height(14.dp))

            VField(theme, s.garageModel, model, KeyboardType.Text) { model = it }
            Spacer(Modifier.height(10.dp))
            VField(theme, s.garageYear, year, KeyboardType.Number) { year = it.filter(Char::isDigit).take(4) }
            Spacer(Modifier.height(10.dp))
            VField(theme, s.garageNickname, nickname, KeyboardType.Text, placeholder = s.garageNicknamePlaceholder) { nickname = it }

            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AppButton(
                    theme = theme,
                    text = s.cancel,
                    onClick = onClose,
                    variant = ButtonVariant.Secondary,
                    modifier = Modifier.weight(1f),
                )
                AppButton(
                    theme = theme,
                    text = s.garageSave,
                    onClick = {
                        val bid = brandId ?: return@AppButton
                        onSave(bid, model.ifBlank { null }, year.toIntOrNull(), nickname)
                    },
                    enabled = brandId != null,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun VField(
    theme: CraftsmenColors,
    label: String,
    value: String,
    keyboardType: KeyboardType,
    placeholder: String = "",
    onChange: (String) -> Unit,
) {
    val setInputFocused = LocalSetInputFocused.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = theme.textDim, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.bgInput)
                .border(1.dp, theme.border, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = value,
                onValueChange = onChange,
                placeholder = { if (placeholder.isNotBlank()) Text(placeholder, color = theme.textMute, fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { setInputFocused(it.isFocused) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
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
