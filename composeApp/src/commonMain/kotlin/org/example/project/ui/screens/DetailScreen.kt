@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import org.example.project.data.CAR_BRANDS
import org.example.project.data.CarBrand
import org.example.project.data.Mechanic
import org.example.project.data.REVIEWS
import org.example.project.data.SPECIALTIES
import org.example.project.data.Specialty
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.BrandMark
import org.example.project.ui.components.IconButton40
import org.example.project.ui.components.MonoLabel
import org.example.project.ui.components.Section
import org.example.project.ui.components.StickyBottom
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconCalendar
import org.example.project.ui.icons.IconChat
import org.example.project.ui.icons.IconChevron
import org.example.project.ui.icons.IconClock
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.IconStarOutline
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun DetailScreen(
    theme: CraftsmenColors,
    brand: CarBrand,
    spec: Specialty,
    mech: Mechanic,
    onBack: () -> Unit,
    onBook: () -> Unit,
    onCall: () -> Unit,
    onWhatsapp: () -> Unit,
    onDirections: () -> Unit,
    onReviews: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            item { HeroBlock(theme, mech, onBack) }
            item { StatStrip(theme, mech) }
            item { BadgesRow(theme, mech) }
            item {
                Section(theme, "About") {
                    Text(
                        text = mech.bio,
                        color = theme.textDim,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                    )
                }
            }
            item {
                Section(theme, "Specialties") {
                    SpecChips(theme, mech, currentSpec = spec)
                }
            }
            item {
                Section(theme, "Brands serviced") {
                    BrandsRow(theme, mech)
                }
            }
            item {
                Section(theme, "Contact & location") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ContactRow(
                            theme, label = "Phone", value = mech.phone, onClick = onCall,
                            icon = { IconPhone(size = 16.dp, color = theme.text) }
                        )
                        ContactRow(
                            theme, label = "WhatsApp", value = mech.phone, onClick = onWhatsapp,
                            icon = { IconChat(size = 16.dp, color = theme.text) }
                        )
                        ContactRow(
                            theme, label = "Address", value = mech.address, onClick = onDirections,
                            icon = { IconPin(size = 16.dp, color = theme.text) }
                        )
                        ContactRow(
                            theme, label = "Hours", value = mech.hours, onClick = null,
                            icon = { IconClock(size = 16.dp, color = theme.text) }
                        )
                    }
                }
            }
            item {
                Section(
                    theme = theme,
                    title = "Recent reviews",
                    right = {
                        Text(
                            text = "See all (${mech.reviews})",
                            color = theme.accent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { onReviews() },
                        )
                    },
                ) {
                    val reviews = REVIEWS.filter { it.mechId == mech.id }.take(2)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        reviews.forEach { ReviewCard(theme, it) }
                    }
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
        StickyBottom(theme) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .clickable(onClick = onCall),
                    contentAlignment = Alignment.Center,
                ) { IconPhone(size = 20.dp, color = theme.text) }
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .clickable(onClick = onWhatsapp),
                    contentAlignment = Alignment.Center,
                ) { IconChat(size = 20.dp, color = theme.text) }
                AppButton(
                    theme = theme,
                    text = "Book appointment",
                    onClick = onBook,
                    modifier = Modifier.weight(1f),
                    leadingIcon = { IconCalendar(size = 18.dp, color = theme.accentText) },
                )
            }
        }
    }
}

@Composable
private fun HeroBlock(theme: CraftsmenColors, mech: Mechanic, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                Brush.verticalGradient(
                    listOf(mech.photo, mech.photo.copy(alpha = 0.8f), theme.bg)
                )
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) { IconBack(size = 20.dp, color = Color.White) }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center,
            ) { IconStarOutline(size = 18.dp, color = Color.White) }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border(3.dp, Color.White, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = mech.initials,
                    color = mech.photo,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(modifier = Modifier.weight(1f).padding(bottom = 4.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = mech.name,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (mech.verified) IconShield(size = 16.dp, color = theme.accent)
                }
                Text(
                    text = mech.shop,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@Composable
private fun StatStrip(theme: CraftsmenColors, mech: Mechanic) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .offset(y = (-28).dp)
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatBlock(theme, label = "RATING", value = mech.rating.toString(), accent = true, modifier = Modifier.weight(1f))
        Box(modifier = Modifier.size(width = 1.dp, height = 30.dp).background(theme.border))
        StatBlock(theme, label = "IN TRADE", value = "${mech.years}y", accent = false, modifier = Modifier.weight(1f))
        Box(modifier = Modifier.size(width = 1.dp, height = 30.dp).background(theme.border))
        StatBlock(theme, label = "KILOMETERS", value = mech.distance.toString(), accent = false, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatBlock(
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

@Composable
private fun BadgesRow(theme: CraftsmenColors, mech: Mechanic) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 4.dp)
            .offset(y = (-24).dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        mech.badges.forEach { b ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, CircleShape)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Text(
                    text = b,
                    color = theme.textDim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun SpecChips(theme: CraftsmenColors, mech: Mechanic, currentSpec: Specialty) {
    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        mech.specialties.forEach { sid ->
            val s = SPECIALTIES.first { it.id == sid }
            val match = sid == currentSpec.id
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (match) theme.accentSoft else theme.bgRaised)
                    .border(1.dp, if (match) theme.accent else theme.border, CircleShape)
                    .padding(horizontal = 11.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                SpecIcon(id = sid, size = 12.dp, color = if (match) theme.accent else theme.textDim)
                Text(
                    text = s.name,
                    color = if (match) theme.accent else theme.text,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun BrandsRow(theme: CraftsmenColors, mech: Mechanic) {
    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        mech.brands.forEach { bid ->
            val b = CAR_BRANDS.first { it.id == bid }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, RoundedCornerShape(10.dp))
                    .padding(start = 6.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BrandMark(brand = b, size = 26.dp, dark = theme.isDark)
                Text(text = b.name, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun ContactRow(
    theme: CraftsmenColors,
    label: String,
    value: String,
    onClick: (() -> Unit)?,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(12.dp))
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(theme.bgInput),
            contentAlignment = Alignment.Center,
        ) { icon() }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                color = theme.textMute,
                fontSize = 10.sp,
                letterSpacing = 0.6.sp,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = value,
                color = theme.text,
                fontSize = 14.sp,
                maxLines = 1,
            )
        }
        if (onClick != null) IconChevron(size = 16.dp, color = theme.textDim)
    }
}

@Composable
fun ReviewCard(theme: CraftsmenColors, review: org.example.project.data.Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(12.dp))
            .padding(14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(theme.borderStrong),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = review.author.first().toString(),
                        color = theme.text,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Column {
                    Text(text = review.author, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = review.date.uppercase(),
                        color = theme.textMute,
                        fontSize = 10.sp,
                    )
                }
            }
            Row {
                repeat(5) { i ->
                    if (i < review.rating) IconStar(size = 11.dp, color = theme.accent)
                    else IconStarOutline(size = 11.dp, color = theme.border)
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = review.text,
            color = theme.textDim,
            fontSize = 13.sp,
            lineHeight = 19.5.sp,
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(theme.bgInput)
                .padding(horizontal = 8.dp, vertical = 3.dp),
        ) {
            Text(text = review.job, color = theme.textDim, fontSize = 10.sp)
        }
    }
}

