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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Shop
import org.example.project.ui.components.Section
import org.example.project.ui.components.StickyBottom
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconChat
import org.example.project.ui.icons.IconChevron
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ShopDetailScreen(
    theme: CraftsmenColors,
    shop: Shop,
    onBack: () -> Unit,
    onCall: () -> Unit,
    onWhatsapp: () -> Unit,
    onDirections: () -> Unit,
) {
    val s = LocalStrings.current
    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(shop.accent, shop.accent.copy(alpha = 0.8f), theme.bg),
                            ),
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 24.dp, top = 48.dp)
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable(onClick = onBack),
                        contentAlignment = Alignment.Center,
                    ) { IconBack(size = 20.dp, color = Color.White) }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = shop.initials,
                                color = shop.accent,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Column(modifier = Modifier.weight(1f).padding(bottom = 4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = shop.name,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                if (shop.verified) IconShield(size = 14.dp, color = theme.accent)
                            }
                            Text(
                                text = s.shopCategoryName(shop.category.id),
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }

            item {
                // Stat strip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(theme.bgCard)
                        .border(1.dp, theme.border, RoundedCornerShape(16.dp))
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    StatCell(theme, label = s.statRating, value = shop.rating.toString(), accent = true, modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.size(width = 1.dp, height = 30.dp).background(theme.border))
                    StatCell(theme, label = s.detailReviewsCount.uppercase(), value = "${shop.reviewCount}", accent = false, modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.size(width = 1.dp, height = 30.dp).background(theme.border))
                    StatCell(theme, label = s.contactPhone.uppercase(), value = if (shop.verified) "✓" else "—", accent = false, modifier = Modifier.weight(1f))
                }
            }

            if (shop.description.isNotBlank()) {
                item {
                    Section(theme, s.about) {
                        Text(text = shop.description, color = theme.textDim, fontSize = 14.sp, lineHeight = 21.sp)
                    }
                }
            }

            if (shop.brands.isNotEmpty()) {
                item {
                    Section(theme, s.brandsServiced) {
                        androidx.compose.foundation.layout.FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            shop.brands.forEach { name ->
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(theme.bgRaised)
                                        .border(1.dp, theme.border, RoundedCornerShape(10.dp))
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = name, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Section(theme, s.contactLocation) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ContactRow(theme, s.contactPhone, shop.phone, onCall) { IconPhone(size = 16.dp, color = theme.text) }
                        ContactRow(theme, s.contactWhatsapp, shop.whatsapp, onWhatsapp) { IconChat(size = 16.dp, color = theme.text) }
                        ContactRow(theme, s.contactAddress, "${shop.address}, ${shop.district}, ${shop.city}", onDirections) { IconPin(size = 16.dp, color = theme.text) }
                        if (shop.website.isNotBlank()) {
                            ContactRow(theme, s.shopVisitWebsite, shop.website, null) { IconChevron(size = 16.dp, color = theme.text) }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(20.dp)) }
        }
        StickyBottom(theme) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .clickable(onClick = onCall),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        IconPhone(size = 18.dp, color = theme.text)
                        Text(s.cardCall, color = theme.text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.accent)
                        .clickable(onClick = onWhatsapp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        IconChat(size = 18.dp, color = theme.accentText)
                        Text(s.cardWhatsapp, color = theme.accentText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCell(
    theme: CraftsmenColors,
    label: String,
    value: String,
    accent: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            if (accent) IconStar(size = 14.dp, color = theme.accent)
            Text(
                text = value,
                color = if (accent) theme.accent else theme.text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            color = theme.textMute,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp,
        )
    }
}
