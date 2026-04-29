package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import org.example.project.data.CarBrand
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconStar
import org.example.project.ui.theme.CraftsmenColors

// ────────── Screen header ──────────
@Composable
fun ScreenHeader(
    theme: CraftsmenColors,
    title: String? = null,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    right: (@Composable () -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth().background(theme.bg)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (onBack != null) {
                    IconButton40(theme = theme, onClick = onBack) {
                        IconBack(size = 20.dp, color = theme.text)
                    }
                } else {
                    Spacer(Modifier.size(40.dp))
                }
                Spacer(Modifier.width(8.dp))
                if (right != null) right() else Spacer(Modifier.size(40.dp))
            }
            if (title != null) {
                Spacer(Modifier.height(14.dp))
                Text(
                    text = title,
                    color = theme.text,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 31.sp,
                )
                if (subtitle != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = subtitle,
                        color = theme.textDim,
                        fontSize = 14.sp,
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
    }
}

// ────────── 40x40 rounded icon button ──────────
@Composable
fun IconButton40(
    theme: CraftsmenColors,
    onClick: () -> Unit,
    background: Color = theme.bgRaised,
    borderColor: Color = theme.border,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { content() }
}

// ────────── Sticky bottom CTA wrapper ──────────
@Composable
fun StickyBottom(theme: CraftsmenColors, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(theme.bg)) {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 16.dp),
        ) { content() }
    }
}

// ────────── Primary action button ──────────
@Composable
fun AppButton(
    theme: CraftsmenColors,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val (bg, fg, bd) = when (variant) {
        ButtonVariant.Primary -> Triple(
            if (enabled) theme.accent else theme.border,
            theme.accentText, Color.Transparent
        )
        ButtonVariant.Secondary -> Triple(Color.Transparent, theme.text, theme.borderStrong)
        ButtonVariant.Ghost -> Triple(theme.bgRaised, theme.text, theme.border)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .border(1.dp, bd, RoundedCornerShape(14.dp))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (leadingIcon != null) leadingIcon()
            Text(
                text = text,
                color = fg.copy(alpha = if (enabled) 1f else 0.5f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

enum class ButtonVariant { Primary, Secondary, Ghost }

// ────────── Stars row ──────────
@Composable
fun Stars(rating: Double, size: Dp, theme: CraftsmenColors) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val full = rating.toInt()
        repeat(5) { i ->
            IconStar(size = size, color = if (i < full) theme.accent else theme.border)
            if (i < 4) Spacer(Modifier.width(1.dp))
        }
    }
}

// ────────── Price tag $$$ ──────────
@Composable
fun PriceTag(price: Int, theme: CraftsmenColors) {
    val annotated = buildAnnotatedString {
        for (i in 1..4) {
            withStyle(SpanStyle(color = theme.text.copy(alpha = if (i <= price) 1f else 0.25f))) {
                append("$")
            }
        }
    }
    Text(text = annotated, fontSize = 12.sp, fontWeight = FontWeight.Medium)
}

// ────────── Pill ──────────
@Composable
fun Pill(
    theme: CraftsmenColors,
    text: String,
    active: Boolean,
    onClick: () -> Unit,
    small: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(if (active) theme.accent else theme.bgRaised)
            .border(1.dp, if (active) theme.accent else theme.border, CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = if (small) 10.dp else 14.dp, vertical = if (small) 4.dp else 8.dp),
    ) {
        Text(
            text = text,
            color = if (active) theme.accentText else theme.text,
            fontSize = if (small) 11.sp else 13.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

// ────────── Mono label (small caps with leading dot) ──────────
@Composable
fun MonoLabel(
    text: String,
    theme: CraftsmenColors,
    color: Color = theme.textDim,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .background(color),
        )
        Text(
            text = text.uppercase(),
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.8.sp,
        )
    }
}

// ────────── Brand mark monogram ──────────
@Composable
fun BrandMark(brand: CarBrand, size: Dp, dark: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(if (dark) Color(0xFF222222) else Color.White)
            .border(1.5.dp, brand.color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = brand.name.first().toString(),
            color = brand.color,
            fontSize = (size.value * 0.42f).sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
                .size(4.dp)
                .clip(CircleShape)
                .background(brand.color),
        )
    }
}

// ────────── Section block ──────────
@Composable
fun Section(
    theme: CraftsmenColors,
    title: String,
    right: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MonoLabel(title, theme)
            if (right != null) right()
        }
        Spacer(Modifier.height(12.dp))
        content()
    }
}
