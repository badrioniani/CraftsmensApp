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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.Shop
import org.example.project.data.ShopCategory
import org.example.project.data.shops.ShopListViewModel
import org.example.project.ui.components.Pill
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconShop
import org.example.project.ui.icons.IconStar
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ShopsScreen(
    theme: CraftsmenColors,
    onPickShop: (Shop) -> Unit,
) {
    val s = LocalStrings.current
    var category by remember { mutableStateOf<ShopCategory?>(null) }

    val vm: ShopListViewModel = viewModel { ShopListViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(category) { vm.load(category?.id) }

    val filtered = state.items

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(theme.bg),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item { ShopsHeader(theme) }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Pill(
                        theme = theme,
                        text = s.shopAnyCategory,
                        active = category == null,
                        onClick = { category = null },
                        small = true,
                    )
                }
                items(ShopCategory.entries.toList()) { c ->
                    Pill(
                        theme = theme,
                        text = s.shopCategoryName(c.id),
                        active = category == c,
                        onClick = { category = c },
                        small = true,
                    )
                }
            }
        }
        if (filtered.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(s.shopsEmptyTitle, color = theme.text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text(s.shopsEmptyBody, color = theme.textDim, fontSize = 13.sp)
                }
            }
        } else {
            items(filtered) { shop ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    ShopCard(theme, shop) { onPickShop(shop) }
                }
            }
        }
    }
}

@Composable
private fun ShopsHeader(theme: CraftsmenColors) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggle = LocalToggleLanguage.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 8.dp),
    ) {
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
            ) { IconShop(size = 18.dp, color = theme.accent) }
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
        Text(
            text = s.shopsTitle,
            color = theme.text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(4.dp))
        Text(text = s.shopsSubtitle, color = theme.textDim, fontSize = 14.sp)
    }
}

@Composable
fun ShopCard(theme: CraftsmenColors, shop: Shop, onClick: () -> Unit) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(shop.accent),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = shop.initials,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = shop.name,
                        color = theme.text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    if (shop.verified) {
                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(theme.accentSoft)
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            IconShield(size = 9.dp, color = theme.accent)
                            Text(s.badgeVerified, color = theme.accent, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(theme.bgInput)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(s.shopCategoryName(shop.category.id), color = theme.textDim, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconPin(size = 11.dp, color = theme.textMute)
                    Text(
                        text = "${shop.district}, ${shop.city}",
                        color = theme.textDim,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    IconStar(size = 13.dp, color = theme.accent)
                    Text(shop.rating.toString(), color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                Text("(${shop.reviewCount})", color = theme.textMute, fontSize = 10.sp)
            }
        }
        if (shop.description.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(text = shop.description, color = theme.textDim, fontSize = 12.sp, lineHeight = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}
