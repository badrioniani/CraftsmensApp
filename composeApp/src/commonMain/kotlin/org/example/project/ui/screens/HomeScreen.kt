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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.Mechanic
import org.example.project.data.mechanics.MechanicListViewModel
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AppLogo
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.components.MechanicCardSkeleton
import org.example.project.ui.components.MechanicListCard
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.IconWrench
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun HomeScreen(
    theme: CraftsmenColors,
    onBrowseCatalog: () -> Unit,
    onPickMech: (Mechanic) -> Unit,
) {
    val s = LocalStrings.current

    val vm: MechanicListViewModel = viewModel { MechanicListViewModel() }
    val listState by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.loadAll() }

    val showSkeleton = listState.loading && listState.items.isEmpty()
    val mechs = listState.items
    val featured = mechs.filter { it.verified }.sortedByDescending { it.rating }.take(4)
    val totalMechs = if (showSkeleton) 0 else mechs.size

    LazyColumn(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        item { HeroBlock(theme, totalMechs, onBrowseCatalog) }
        item { ValuePropsGrid(theme) }
        item { FeaturedHeader(theme) }
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (showSkeleton) {
                    repeat(4) { MechanicCardSkeleton(theme) }
                } else {
                    featured.forEach { m ->
                        MechanicListCard(theme = theme, mech = m, onClick = { onPickMech(m) })
                    }
                }
            }
        }
        item { Spacer(Modifier.height(20.dp)) }
        item {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                AppButton(
                    theme = theme,
                    text = s.featuredViewAll,
                    onClick = onBrowseCatalog,
                    variant = ButtonVariant.Secondary,
                )
            }
        }
        item { Spacer(Modifier.height(28.dp)) }
    }
}

@Composable
private fun HeroBlock(theme: CraftsmenColors, total: Int, onCta: () -> Unit) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggleLang = LocalToggleLanguage.current
    val heroBg = if (theme.isDark)
        Brush.verticalGradient(listOf(Color(0xFF0E0E0E), Color(0xFF1A1A1A)))
    else
        Brush.verticalGradient(listOf(Color(0xFF1F2A37), Color(0xFF111827)))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(heroBg)
            .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppLogo(size = 32.dp)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = s.appName,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.dp, Color.White.copy(alpha = 0.12f), CircleShape)
                    .clickable { toggleLang() }
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = lang.display,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
                Box(modifier = Modifier.size(width = 1.dp, height = 12.dp).background(Color.White.copy(alpha = 0.18f)))
                Text(
                    text = if (lang.code == "en") "KA" else "EN",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                )
            }
        }
        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f))
                .border(1.dp, Color.White.copy(alpha = 0.10f), CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(theme.accent))
            Text(
                text = s.homeEyebrow,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = s.homeTitleA,
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
        )
        Text(
            text = s.homeTitleB,
            color = theme.accent,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = s.homeSubtitle,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            lineHeight = 21.sp,
        )

        Spacer(Modifier.height(20.dp))
        AppButton(
            theme = theme,
            text = s.homeBrowseCatalog,
            onClick = onCta,
        )
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            StatPair(Color.White, theme.textDim, "$total+", s.statMechanics)
            StatPair(Color.White, theme.textDim, "30+", s.statBrands)
            StatPair(Color.White, theme.textDim, "4.6", s.statAvgRating)
        }
    }
}

@Composable
private fun StatPair(fg: Color, sub: Color, value: String, label: String) {
    Column {
        Text(text = value, color = fg, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(2.dp))
        Text(
            text = label.uppercase(),
            color = sub,
            fontSize = 10.sp,
            letterSpacing = 0.7.sp,
        )
    }
}

@Composable
private fun ValuePropsGrid(theme: CraftsmenColors) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        ValueCard(theme, "verified", s.valueVerifiedTitle, s.valueVerifiedBody)
        ValueCard(theme, "reviews", s.valueReviewsTitle, s.valueReviewsBody)
        ValueCard(theme, "fees", s.valueFeesTitle, s.valueFeesBody)
    }
}

@Composable
private fun ValueCard(theme: CraftsmenColors, iconId: String, title: String, body: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(theme.accentSoft),
            contentAlignment = Alignment.Center,
        ) {
            when (iconId) {
                "verified" -> IconShield(size = 18.dp, color = theme.accent)
                "reviews" -> IconStar(size = 18.dp, color = theme.accent)
                else -> IconWrench(size = 18.dp, color = theme.accent)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(text = body, color = theme.textDim, fontSize = 12.sp, lineHeight = 18.sp)
        }
    }
}

@Composable
private fun FeaturedHeader(theme: CraftsmenColors) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 12.dp),
    ) {
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(theme.accentSoft)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            IconWrench(size = 12.dp, color = theme.accent, stroke = 2f)
            Text(
                text = s.featuredChip,
                color = theme.accent,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.7.sp,
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = s.featuredTitle,
            color = theme.text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = s.featuredSubtitle,
            color = theme.textDim,
            fontSize = 13.sp,
        )
    }
}
