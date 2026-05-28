package org.example.project.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun AppSpinner(
    size: Dp = 24.dp,
    color: Color,
    track: Color = color.copy(alpha = 0.18f),
    stroke: Dp = 2.5.dp,
) {
    val transition = rememberInfiniteTransition(label = "spinner")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "rotation",
    )
    Canvas(modifier = Modifier.size(size).rotate(angle)) {
        val sweep = 270f
        val strokePx = stroke.toPx()
        drawArc(
            color = track,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokePx, cap = StrokeCap.Round),
        )
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = sweep,
            useCenter = false,
            style = Stroke(width = strokePx, cap = StrokeCap.Round),
        )
    }
}

@Composable
fun LoadingRow(theme: CraftsmenColors, label: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AppSpinner(size = 20.dp, color = theme.accent)
        Spacer(Modifier.size(10.dp))
        Text(label, color = theme.textDim, fontSize = 13.sp)
    }
}

@Composable
fun LoadingOverlay(theme: CraftsmenColors, label: String? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.32f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(theme.bgRaised)
                .border(1.dp, theme.border, RoundedCornerShape(18.dp))
                .padding(horizontal = 28.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppSpinner(size = 28.dp, color = theme.accent)
            if (label != null) {
                Spacer(Modifier.size(10.dp))
                Text(label, color = theme.text, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun MechanicCardSkeleton(theme: CraftsmenColors) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "alpha",
    )
    val block = theme.bgInput.copy(alpha = alpha)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(18.dp))
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(block))
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth(0.65f).height(14.dp).clip(RoundedCornerShape(4.dp)).background(block))
                Spacer(Modifier.size(8.dp))
                Box(modifier = Modifier.fillMaxWidth(0.45f).height(11.dp).clip(RoundedCornerShape(4.dp)).background(block))
                Spacer(Modifier.size(10.dp))
                Box(modifier = Modifier.fillMaxWidth(0.55f).height(10.dp).clip(RoundedCornerShape(4.dp)).background(block))
            }
        }
        Spacer(Modifier.size(12.dp))
        Box(modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(4.dp)).background(block))
        Spacer(Modifier.size(6.dp))
        Box(modifier = Modifier.fillMaxWidth(0.8f).height(12.dp).clip(RoundedCornerShape(4.dp)).background(block))
        Spacer(Modifier.size(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f).height(36.dp).clip(RoundedCornerShape(12.dp)).background(block))
            Box(modifier = Modifier.weight(1f).height(36.dp).clip(RoundedCornerShape(12.dp)).background(block))
        }
    }
}
