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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.AutoPart
import org.example.project.ui.components.IconButton40
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconChat
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun PartDetailScreen(
    theme: CraftsmenColors,
    part: AutoPart,
    onBack: () -> Unit,
    onCall: () -> Unit,
    onWhatsapp: () -> Unit,
    onChat: () -> Unit,
) {
    val s = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bg)
            .verticalScroll(rememberScrollState()),
    ) {
        // Header with back + share placeholder
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton40(theme = theme, onClick = onBack) {
                IconBack(size = 20.dp, color = theme.text)
            }
            // Stock chip
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (part.inStock) theme.success.copy(alpha = 0.15f) else theme.danger.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (part.inStock) theme.success else theme.danger),
                )
                Text(
                    text = if (part.inStock) s.inStockLabel else s.outOfStock,
                    color = if (part.inStock) theme.success else theme.danger,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Hero visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(part.accent.copy(alpha = 0.18f))
                .height(220.dp),
            contentAlignment = Alignment.Center,
        ) {
            SpecIcon(
                id = part.categoryId,
                size = 96.dp,
                color = part.accent,
            )
        }

        Spacer(Modifier.height(20.dp))

        // Title block
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(theme.accentSoft)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = part.brand,
                        color = theme.accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.4.sp,
                    )
                }
                Spacer(Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, theme.border, CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = s.conditionLabel(part.condition.name),
                        color = theme.textDim,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = part.name,
                color = theme.text,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 28.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = formatPrice(part.priceCents, part.currency),
                color = theme.text,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(Modifier.height(20.dp))

        // About section
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            MonoLabel(s.partAboutSection, theme)
            Spacer(Modifier.height(10.dp))
            Text(
                text = part.summary,
                color = theme.textDim,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )
            Spacer(Modifier.height(12.dp))

            // Fits chips
            val fitsLabels = if (part.fitsBrandIds.isEmpty()) listOf(s.fitsAnyLabel)
            else part.fitsBrandIds.map { s.fitsLabel(it.uppercase()) }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                fitsLabels.take(3).forEach { lbl ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(theme.bgRaised)
                            .border(1.dp, theme.border, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    ) {
                        Text(
                            text = lbl,
                            color = theme.textDim,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Seller card
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            MonoLabel(s.sellerSection, theme)
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(theme.bgCard)
                    .border(1.dp, theme.border, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.accentSoft),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = part.sellerName.take(1).uppercase(),
                        color = theme.accent,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = part.sellerName,
                        color = theme.text,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        IconStar(size = 12.dp, color = theme.accent)
                        Text(
                            text = part.sellerRating.toString(),
                            color = theme.textDim,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "·",
                            color = theme.textMute,
                            fontSize = 12.sp,
                        )
                        IconPin(size = 12.dp, color = theme.textMute)
                        Text(
                            text = part.sellerCity,
                            color = theme.textDim,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Connect actions
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            ContactRow(
                theme = theme,
                label = s.callSeller,
                value = part.sellerPhone,
                onClick = onCall,
                icon = { IconPhone(size = 18.dp, color = theme.text) },
            )
            Spacer(Modifier.height(10.dp))
            ContactRow(
                theme = theme,
                label = s.whatsappSeller,
                value = part.sellerWhatsapp,
                onClick = onWhatsapp,
                icon = { IconChat(size = 18.dp, color = theme.text) },
                accent = true,
            )
            Spacer(Modifier.height(10.dp))
            ContactRow(
                theme = theme,
                label = s.chatSeller,
                value = part.sellerName,
                onClick = onChat,
                icon = { IconChat(size = 18.dp, color = theme.text) },
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun ContactRow(
    theme: CraftsmenColors,
    label: String,
    value: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    accent: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (accent) theme.accentSoft else theme.bgRaised)
            .border(1.dp, if (accent) theme.accent else theme.border, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(theme.bg),
            contentAlignment = Alignment.Center,
        ) { icon() }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = if (accent) theme.accent else theme.textDim,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = value,
                color = theme.text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
