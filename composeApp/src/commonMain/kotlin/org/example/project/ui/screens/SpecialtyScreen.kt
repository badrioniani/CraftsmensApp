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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.CarBrand
import org.example.project.data.SPECIALTIES
import org.example.project.data.Specialty
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.BrandMark
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.components.StickyBottom
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun SpecialtyScreen(
    theme: CraftsmenColors,
    brand: CarBrand,
    onBack: () -> Unit,
    onPickSpec: (Specialty) -> Unit,
) {
    val s = LocalStrings.current
    var selected by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        ScreenHeader(
            theme = theme,
            title = s.specQuestion,
            subtitle = "${s.forYour} ${brand.name.uppercase()}",
            onBack = onBack,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item(span = { GridItemSpan(2) }) {
                BrandHeaderCard(brand, theme)
            }
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                ) { MonoLabel(s.pickSpecialty, theme) }
            }
            items(SPECIALTIES) { spec ->
                SpecCard(
                    spec = spec,
                    theme = theme,
                    selected = selected == spec.id,
                    onClick = { selected = spec.id },
                )
            }
            item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(16.dp)) }
        }
        StickyBottom(theme) {
            val sel = SPECIALTIES.firstOrNull { it.id == selected }
            AppButton(
                theme = theme,
                text = if (sel != null) s.findCraftsmenFor(s.specialtyName(sel.id)) else s.selectSpecialty,
                onClick = { sel?.let { onPickSpec(it) } },
                enabled = sel != null,
            )
        }
    }
}

@Composable
private fun BrandHeaderCard(brand: CarBrand, theme: CraftsmenColors) {
    val s = LocalStrings.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(theme.bgRaised)
            .border(1.dp, theme.border, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BrandMark(brand = brand, size = 40.dp, dark = theme.isDark)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = brand.name,
                color = theme.text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "${s.estPrefix} ${brand.founded} · ${s.country(brand.country).uppercase()}",
                color = theme.textDim,
                fontSize = 11.sp,
            )
        }
        Text(
            text = s.change,
            color = theme.accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { },
        )
    }
}

@Composable
private fun SpecCard(spec: Specialty, theme: CraftsmenColors, selected: Boolean, onClick: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) theme.accentSoft else theme.bgCard)
            .border(
                width = 1.5.dp,
                color = if (selected) theme.accent else theme.border,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) theme.accent else theme.bgInput),
            contentAlignment = Alignment.Center,
        ) {
            SpecIcon(
                id = spec.id,
                size = 22.dp,
                color = if (selected) theme.accentText else theme.text,
            )
        }
        Column {
            Text(
                text = s.specialtyName(spec.id),
                color = theme.text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = s.specialtyDesc(spec.id),
                color = theme.textDim,
                fontSize = 11.sp,
                lineHeight = 15.sp,
            )
        }
        Text(
            text = "${spec.jobs} ${s.jobsDone}",
            color = theme.textMute,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp,
        )
    }
}
