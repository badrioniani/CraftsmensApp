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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.AUTO_PARTS
import org.example.project.data.AutoPart
import org.example.project.data.PART_CATEGORIES
import org.example.project.data.PartCategory
import org.example.project.ui.components.AppLogo
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.components.Stars
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconSearch
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.IconX
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ShopScreen(
    theme: CraftsmenColors,
    onPickPart: (AutoPart) -> Unit,
) {
    val s = LocalStrings.current
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("all") }

    val filtered = remember(query, category) {
        AUTO_PARTS.filter { p ->
            (category == "all" || p.categoryId == category) &&
                (query.isBlank()
                    || p.name.contains(query, ignoreCase = true)
                    || p.brand.contains(query, ignoreCase = true)
                    || p.sellerName.contains(query, ignoreCase = true))
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().background(theme.bg),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(2) }) { ShopHeader(theme, query, { query = it }) }

        item(span = { GridItemSpan(2) }) {
            CategoryRow(
                theme = theme,
                categories = PART_CATEGORIES,
                selectedId = category,
                onSelect = { category = it },
            )
        }

        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MonoLabel(s.partsCount, theme)
                Text(
                    text = "${filtered.size}/${AUTO_PARTS.size}",
                    fontSize = 11.sp,
                    color = theme.textMute,
                )
            }
        }

        if (filtered.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                EmptyState(theme, onReset = { query = ""; category = "all" })
            }
        } else {
            items(filtered, key = { it.id }) { part ->
                PartCard(part = part, theme = theme, onClick = { onPickPart(part) })
            }
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(20.dp)) }
    }
}

@Composable
private fun ShopHeader(theme: CraftsmenColors, q: String, onQ: (String) -> Unit) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggleLang = LocalToggleLanguage.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val setInputFocused = LocalSetInputFocused.current

    Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppLogo(theme = theme, size = 32.dp)
                Spacer(Modifier.size(8.dp))
                Text(
                    text = s.appName,
                    color = theme.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
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
                Box(modifier = Modifier.size(width = 1.dp, height = 12.dp).background(theme.border))
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
            text = s.shopTitle1,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Text(
            text = s.shopTitle2,
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
                placeholder = { Text(s.shopSearchPlaceholder, color = theme.textDim) },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { setInputFocused(it.isFocused) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }),
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
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun CategoryRow(
    theme: CraftsmenColors,
    categories: List<PartCategory>,
    selectedId: String,
    onSelect: (String) -> Unit,
) {
    val s = LocalStrings.current
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(end = 8.dp),
    ) {
        items(categories) { cat ->
            val active = cat.id == selectedId
            val label = if (cat.id == "all") s.partsCategoryAll else s.specialtyName(cat.id)
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (active) theme.accent else theme.bgRaised)
                    .border(1.dp, if (active) theme.accent else theme.border, CircleShape)
                    .clickable { onSelect(cat.id) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                if (cat.id != "all") {
                    SpecIcon(
                        id = cat.id,
                        size = 14.dp,
                        color = if (active) theme.accentText else theme.text,
                    )
                }
                Text(
                    text = label,
                    color = if (active) theme.accentText else theme.text,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun PartCard(part: AutoPart, theme: CraftsmenColors, onClick: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
    ) {
        // Visual block (icon over accent wash)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(part.accent.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center,
        ) {
            SpecIcon(
                id = part.categoryId,
                size = 44.dp,
                color = part.accent,
            )
            // Stock chip
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(if (part.inStock) theme.success.copy(alpha = 0.15f) else theme.danger.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(if (part.inStock) theme.success else theme.danger),
                )
                Text(
                    text = if (part.inStock) s.inStockLabel else s.outOfStock,
                    color = if (part.inStock) theme.success else theme.danger,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.4.sp,
                )
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = part.name,
                color = theme.text,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                lineHeight = 16.sp,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = part.brand,
                color = theme.textMute,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.6.sp,
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = formatPrice(part.priceCents, part.currency),
                color = theme.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                IconStar(size = 11.dp, color = theme.accent)
                Text(
                    text = part.sellerRating.toString(),
                    color = theme.textDim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "·",
                    color = theme.textMute,
                    fontSize = 11.sp,
                )
                Text(
                    text = part.sellerCity,
                    color = theme.textDim,
                    fontSize = 11.sp,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun EmptyState(theme: CraftsmenColors, onReset: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = s.noPartsMatch,
            color = theme.textDim,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, theme.borderStrong, CircleShape)
                .clickable(onClick = onReset)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = s.resetSearch,
                color = theme.text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

internal fun formatPrice(cents: Int, currency: String): String {
    val whole = cents / 100
    val fraction = cents % 100
    val symbol = when (currency) {
        "USD" -> "$"
        "EUR" -> "€"
        "GEL" -> "₾"
        else -> "$currency "
    }
    val frac = if (fraction == 0) "" else "." + fraction.toString().padStart(2, '0')
    return "$symbol$whole$frac"
}
