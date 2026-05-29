package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.ui.icons.IconWrench

private val BrandBadge = Color(0xFF0B0D0E)
private val BrandAccent = Color(0xFFF59E0B)

@Composable
fun AppLogo(size: Dp = 40.dp, modifier: Modifier = Modifier) {
    val corner = (size.value * 0.22f).dp
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(BrandBadge),
        contentAlignment = Alignment.Center,
    ) {
        IconWrench(size = (size.value * 0.55f).dp, color = BrandAccent, stroke = 2f)
    }
}
