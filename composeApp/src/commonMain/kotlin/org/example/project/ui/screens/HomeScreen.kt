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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.CAR_BRANDS
import org.example.project.data.CarBrand
import org.example.project.ui.components.BrandMark
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconSearch
import org.example.project.ui.icons.IconX
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun HomeScreen(theme: CraftsmenColors, onPickBrand: (CarBrand) -> Unit) {
    val s = LocalStrings.current
    var query by remember { mutableStateOf("") }
    val filtered = CAR_BRANDS.filter {
        it.name.contains(query, ignoreCase = true) || it.country.contains(query, ignoreCase = true)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().background(theme.bg),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item(span = { GridItemSpan(3) }) { HomeHeader(theme, query, { query = it }) }
        item(span = { GridItemSpan(3) }) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MonoLabel(s.pickBrand, theme)
                Text(
                    text = "${filtered.size}/${CAR_BRANDS.size}",
                    fontSize = 11.sp,
                    color = theme.textMute,
                )
            }
        }
        items(filtered) { brand ->
            BrandTile(brand, theme, onClick = { onPickBrand(brand) })
        }
        item(span = { GridItemSpan(3) }) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp)) {
                MonoLabel(s.dontSeeBrand, theme)
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, theme.borderStrong, RoundedCornerShape(14.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = s.addManually,
                        color = theme.textDim,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(theme: CraftsmenColors, q: String, onQ: (String) -> Unit) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggleLang = LocalToggleLanguage.current
    Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(theme.accent),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "c",
                        color = theme.accentText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = s.appName,
                    color = theme.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            // Language toggle pill
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, CircleShape)
                    .clickable { toggleLang() }
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = lang.display,
                    color = theme.text,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
                Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 12.dp)
                        .background(theme.border),
                )
                Text(
                    text = if (lang.code == "en") "KA" else "EN",
                    color = theme.textMute,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                )
            }
        }
        Spacer(Modifier.height(22.dp))
        Text(
            text = s.homeTitle1,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Text(
            text = s.homeTitle2,
            color = theme.textDim,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(theme.bgInput)
                .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            IconSearch(size = 18.dp, color = theme.textDim)
            TextField(
                value = q,
                onValueChange = onQ,
                placeholder = { Text(s.searchPlaceholder, color = theme.textDim) },
                modifier = Modifier.weight(1f),
                singleLine = true,
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
            if (q.isNotEmpty()) {
                Box(modifier = Modifier.clickable { onQ("") }) {
                    IconX(size = 16.dp, color = theme.textDim)
                }
            }
        }
        Spacer(Modifier.height(18.dp))
    }
}

@Composable
private fun BrandTile(brand: CarBrand, theme: CraftsmenColors, onClick: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        BrandMark(brand = brand, size = 48.dp, dark = theme.isDark)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = brand.name,
                color = theme.text,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = s.country(brand.country).uppercase(),
                color = theme.textMute,
                fontSize = 9.sp,
                letterSpacing = 0.7.sp,
            )
        }
    }
}
