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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.CarBrand
import org.example.project.data.MECHANICS
import org.example.project.data.Mechanic
import org.example.project.data.Specialty
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.components.IconButton40
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.components.Pill
import org.example.project.ui.components.PriceTag
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.icons.IconFilter
import org.example.project.ui.icons.IconMap
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.IconX
import org.example.project.ui.theme.CraftsmenColors

data class MechFilters(
    val minRating: Double = 0.0,
    val minYears: Int = 0,
    val maxDistance: Double = 10.0,
    val maxPrice: Int = 4,
    val availableNow: Boolean = false,
)

enum class SortMode { Distance, Rating, Experience }
enum class ListLayout { List, Grid }

@Composable
fun ListScreen(
    theme: CraftsmenColors,
    brand: CarBrand,
    spec: Specialty,
    layout: ListLayout,
    onBack: () -> Unit,
    onPickMech: (Mechanic) -> Unit,
    onMap: () -> Unit,
) {
    var filters by remember { mutableStateOf(MechFilters()) }
    var sort by remember { mutableStateOf(SortMode.Distance) }
    var filterOpen by remember { mutableStateOf(false) }

    fun matches(m: Mechanic, useBrand: Boolean): Boolean {
        if (!m.specialties.contains(spec.id)) return false
        if (useBrand && !m.brands.contains(brand.id)) return false
        if (m.rating < filters.minRating) return false
        if (m.years < filters.minYears) return false
        if (m.distance > filters.maxDistance) return false
        if (m.price > filters.maxPrice) return false
        if (filters.availableNow && !m.available) return false
        return true
    }

    var primary = MECHANICS.filter { matches(it, useBrand = true) }
    val fallback = primary.isEmpty()
    if (fallback) primary = MECHANICS.filter { matches(it, useBrand = false) }
    val sorted = when (sort) {
        SortMode.Distance -> primary.sortedBy { it.distance }
        SortMode.Rating -> primary.sortedByDescending { it.rating }
        SortMode.Experience -> primary.sortedByDescending { it.years }
    }

    Box(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(
                theme = theme,
                onBack = onBack,
                title = spec.name,
                subtitle = "${brand.name.uppercase()} · ${sorted.size} CRAFTSMEN",
                right = {
                    IconButton40(theme = theme, onClick = onMap) {
                        IconMap(size = 18.dp, color = theme.text)
                    }
                },
            )

            // Filter / sort row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(theme.bg)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(theme.bgRaised)
                            .border(1.dp, theme.border, CircleShape)
                            .clickable { filterOpen = true }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        IconFilter(size = 13.dp, color = theme.text)
                        Text("Filters", color = theme.text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        if (filters.minRating > 0 || filters.minYears > 0 || filters.availableNow || filters.maxPrice < 4) {
                            Box(
                                modifier = Modifier.size(6.dp).clip(CircleShape).background(theme.accent)
                            )
                        }
                    }
                }
                items(SortMode.values().toList()) { mode ->
                    Pill(
                        theme = theme,
                        text = when (mode) {
                            SortMode.Distance -> "Nearest"
                            SortMode.Rating -> "Top rated"
                            SortMode.Experience -> "Most experience"
                        },
                        active = sort == mode,
                        onClick = { sort = mode },
                        small = true,
                    )
                }
            }

            if (fallback) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(theme.accentSoft)
                        .border(1.dp, theme.accent, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("!", color = theme.accent, fontWeight = FontWeight.Bold)
                        Text(
                            "No ${brand.name} specialists for ${spec.name.lowercase()} nearby. Showing other ${spec.name.lowercase()} craftsmen.",
                            color = theme.text,
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            if (sorted.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("No craftsmen match your filters.", color = theme.textDim, fontSize = 14.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Reset filters",
                        color = theme.accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { filters = MechFilters() },
                    )
                }
            } else if (layout == ListLayout.Grid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(sorted) { m -> MechGridCard(m, theme) { onPickMech(m) } }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(sorted) { m -> MechListCard(m, theme) { onPickMech(m) } }
                }
            }
        }

        if (filterOpen) {
            FilterSheet(
                theme = theme,
                filters = filters,
                onApply = { filters = it; filterOpen = false },
                onClose = { filterOpen = false },
            )
        }
    }
}

@Composable
private fun MechListCard(mech: Mechanic, theme: CraftsmenColors, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(mech.photo),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = mech.initials,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            if (mech.available) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 0.dp, bottom = 0.dp)
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(theme.success)
                        .border(2.dp, theme.bgCard, CircleShape),
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = mech.name,
                    color = theme.text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                if (mech.verified) IconShield(size = 13.dp, color = theme.accent)
            }
            Text(text = mech.shop, color = theme.textDim, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconStar(size = 12.dp, color = theme.accent)
                    Text(mech.rating.toString(), color = theme.text, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("(${mech.reviews})", color = theme.textMute, fontSize = 11.sp)
                }
                Text("${mech.years}y exp", color = theme.textDim, fontSize = 11.sp)
                PriceTag(price = mech.price, theme = theme)
            }
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = mech.distance.toString(),
                    color = theme.text,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(text = " km", color = theme.textDim, fontSize = 10.sp)
            }
            if (mech.available) MonoLabel("Open now", theme, color = theme.success)
            else MonoLabel("Closed", theme, color = theme.textMute)
        }
    }
}

@Composable
private fun MechGridCard(mech: Mechanic, theme: CraftsmenColors, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(mech.photo),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = mech.initials,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
            )
            if (mech.available) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(theme.success)
                        .border(2.dp, Color.White, CircleShape),
                )
            }
        }
        Column {
            Text(
                text = mech.name,
                color = theme.text,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(text = mech.shop, color = theme.textDim, fontSize = 11.sp)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconStar(size = 11.dp, color = theme.accent)
                Text(mech.rating.toString(), color = theme.text, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
            Text("${mech.distance}km", color = theme.textDim, fontSize = 10.sp)
        }
    }
}

// ───── FilterSheet ─────
@Composable
private fun FilterSheet(
    theme: CraftsmenColors,
    filters: MechFilters,
    onApply: (MechFilters) -> Unit,
    onClose: () -> Unit,
) {
    var local by remember { mutableStateOf(filters) }
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
                Text("Filters", color = theme.text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Box(modifier = Modifier.clickable { onClose() }.padding(4.dp)) {
                    IconX(size = 20.dp, color = theme.textDim)
                }
            }

            FilterRow(theme, "Minimum rating") {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(0.0, 4.0, 4.5, 4.8).forEach { r ->
                        Pill(
                            theme = theme,
                            text = if (r == 0.0) "Any" else "$r+",
                            active = local.minRating == r,
                            onClick = { local = local.copy(minRating = r) },
                            small = true,
                        )
                    }
                }
            }
            FilterRow(theme, "Years of experience") {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(0, 5, 10, 20).forEach { y ->
                        Pill(
                            theme = theme,
                            text = if (y == 0) "Any" else "$y+ yrs",
                            active = local.minYears == y,
                            onClick = { local = local.copy(minYears = y) },
                            small = true,
                        )
                    }
                }
            }
            FilterRow(theme, "Max distance: ${local.maxDistance.toInt()} km") {
                // Simple stepper buttons since no slider in commonMain default
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(2.0, 5.0, 10.0).forEach { d ->
                        Pill(
                            theme = theme,
                            text = "$d km",
                            active = local.maxDistance == d,
                            onClick = { local = local.copy(maxDistance = d) },
                            small = true,
                        )
                    }
                }
            }
            FilterRow(theme, "Max price") {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (1..4).forEach { p ->
                        Pill(
                            theme = theme,
                            text = "$".repeat(p) + if (p == 4) "" else "+",
                            active = local.maxPrice == p,
                            onClick = { local = local.copy(maxPrice = p) },
                            small = true,
                        )
                    }
                }
            }
            FilterRow(theme, "Available today") {
                ToggleSwitch(
                    on = local.availableNow,
                    accent = theme.accent,
                    track = theme.border,
                    onToggle = { local = local.copy(availableNow = !local.availableNow) },
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AppButton(
                    theme = theme,
                    text = "Reset",
                    onClick = { local = MechFilters() },
                    variant = ButtonVariant.Secondary,
                    modifier = Modifier.weight(1f),
                )
                AppButton(
                    theme = theme,
                    text = "Apply filters",
                    onClick = { onApply(local) },
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FilterRow(theme: CraftsmenColors, label: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .border(width = 0.dp, color = Color.Transparent),
    ) {
        Text(
            text = label,
            color = theme.text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(10.dp))
        content()
        Spacer(Modifier.height(14.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
    }
}

@Composable
private fun ToggleSwitch(on: Boolean, accent: Color, track: Color, onToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 50.dp, height = 28.dp)
            .clip(CircleShape)
            .background(if (on) accent else track)
            .clickable(onClick = onToggle),
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(if (on) Alignment.CenterEnd else Alignment.CenterStart),
        )
    }
}
