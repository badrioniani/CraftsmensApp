package org.example.project.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.CarBrand
import org.example.project.data.MECHANICS
import org.example.project.data.Mechanic
import org.example.project.data.Specialty
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconChevron
import org.example.project.ui.icons.IconStar
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun MapScreen(
    theme: CraftsmenColors,
    brand: CarBrand,
    spec: Specialty,
    onBack: () -> Unit,
    onPickMech: (Mechanic) -> Unit,
) {
    val s = LocalStrings.current
    val mechs = MECHANICS.filter { it.specialties.contains(spec.id) }
    var active by remember { mutableStateOf(mechs.firstOrNull()) }

    val positions = mechs.mapIndexed { i, m ->
        val seed = m.id[1].code
        Offset(
            x = (20 + (seed * 13) % 60).toFloat() / 100f,
            y = (20 + (seed * 7 + i * 23) % 50).toFloat() / 100f,
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val mw = this.maxWidth
            val mh = this.maxHeight
            MapBackground(theme)

            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) { IconBack(size = 20.dp, color = theme.text) }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    SpecIcon(id = spec.id, size = 16.dp, color = theme.text)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${s.specialtyName(spec.id)} · ${brand.name}",
                            color = theme.text,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "${mechs.size} ${s.nearby}",
                            color = theme.textMute,
                            fontSize = 10.sp,
                        )
                    }
                }
            }

            // Pins (positioned absolutely using offset)
            mechs.forEachIndexed { i, m ->
                val pos = positions[i]
                val isActive = active?.id == m.id
                Box(
                    modifier = Modifier
                        .offset(
                            x = (mw * pos.x) - 28.dp,
                            y = (mh * pos.y) - 24.dp,
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isActive) theme.accent else theme.bgCard)
                            .border(2.dp, if (isActive) theme.accent else theme.border, CircleShape)
                            .clickable { active = m }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        IconStar(
                            size = 11.dp,
                            color = if (isActive) theme.accentText else theme.accent,
                        )
                        Text(
                            text = m.rating.toString(),
                            color = if (isActive) theme.accentText else theme.text,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            // "You are here"
            Box(
                modifier = Modifier
                    .offset(
                        x = mw * 0.5f - 9.dp,
                        y = mh * 0.6f - 9.dp,
                    )
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(theme.accent)
                    .border(3.dp, Color.White, CircleShape),
            )
        }

        // Bottom card
        active?.let { m ->
            Column(modifier = Modifier.fillMaxWidth().background(theme.bg)) {
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 14.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(theme.bgCard)
                        .border(1.dp, theme.border, RoundedCornerShape(16.dp))
                        .clickable { onPickMech(m) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(m.photo),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = m.initials,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = m.name, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = m.shop, color = theme.textDim, fontSize = 12.sp)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                IconStar(size = 11.dp, color = theme.accent)
                                Text(
                                    text = m.rating.toString(),
                                    color = theme.text,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Text(text = "${m.distance}km · ${m.years}y", color = theme.textDim, fontSize = 11.sp)
                        }
                    }
                    IconChevron(size = 18.dp, color = theme.textDim)
                }
            }
        }
    }
}

@Composable
private fun MapBackground(theme: CraftsmenColors) {
    val blockColor = if (theme.isDark) Color(0xFF161616) else Color(0xFFF0EDE6)
    val lineColor = if (theme.isDark) Color(0xFF1F1F1F) else Color(0xFFE9E7E1)
    val roadColor = if (theme.isDark) Color(0xFF2A2A2A) else Color(0xFFDCD9D2)
    val laneColor = if (theme.isDark) Color(0xFF3A3A3A) else Color.White

    Canvas(modifier = Modifier.fillMaxSize().background(blockColor)) {
        val w = size.width
        val h = size.height
        val sx = w / 412f
        val sy = h / 600f

        // Soft blocks
        for (r in 0..5) {
            for (c in 0..4) {
                val bw = (62 + (r * 7 % 30)).toFloat() * sx
                val bh = (70 + (c * 5 % 40)).toFloat() * sy
                val bx = (c * 82 + (if (r % 2 == 1) 10 else 0)).toFloat() * sx
                val by = (r * 100 + 20).toFloat() * sy
                drawRect(
                    color = if (theme.isDark) Color(0xFF1C1C1C) else Color(0xFFF7F4EC),
                    topLeft = Offset(bx, by),
                    size = Size(bw, bh),
                )
                drawRect(
                    color = lineColor,
                    topLeft = Offset(bx, by),
                    size = Size(bw, bh),
                    style = Stroke(width = 1f),
                )
            }
        }

        // Roads
        drawLine(roadColor, Offset(0f, 180f * sy), Offset(w, 180f * sy), strokeWidth = 14f * sy)
        drawLine(roadColor, Offset(0f, 380f * sy), Offset(w, 380f * sy), strokeWidth = 10f * sy)
        drawLine(roadColor, Offset(120f * sx, 0f), Offset(120f * sx, h), strokeWidth = 10f * sx)
        drawLine(roadColor, Offset(290f * sx, 0f), Offset(290f * sx, h), strokeWidth = 14f * sx)

        // Lane dashes
        val dash = PathEffect.dashPathEffect(floatArrayOf(6f, 8f))
        drawLine(
            laneColor, Offset(0f, 180f * sy), Offset(w, 180f * sy),
            strokeWidth = 1f, pathEffect = dash,
        )
        drawLine(
            laneColor, Offset(290f * sx, 0f), Offset(290f * sx, h),
            strokeWidth = 1f, pathEffect = dash,
        )
    }
}
