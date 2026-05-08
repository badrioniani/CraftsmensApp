package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.ui.icons.IconWrench
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun AppLogo(theme: CraftsmenColors, size: Dp = 40.dp, modifier: Modifier = Modifier) {
    val corner = (size.value * 0.28f).dp
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(theme.accent),
        contentAlignment = Alignment.Center,
    ) {
        IconWrench(size = (size.value * 0.55f).dp, color = theme.accentText, stroke = 2f)
    }
}
