package org.example.project.ui.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Draws a 24x24 viewport icon at the requested size.
 * `draw` is called inside a scaled DrawScope so coordinates can be expressed
 * in the original 24x24 SVG-style space.
 */
@Composable
fun LineIcon(
    size: Dp,
    color: Color,
    modifier: Modifier = Modifier,
    stroke: Float = 1.75f,
    draw: DrawScope.(stroke: Stroke, color: Color) -> Unit,
) {
    Canvas(modifier = modifier.size(size)) {
        scale(scaleX = this.size.width / 24f, scaleY = this.size.height / 24f, pivot = Offset.Zero) {
            val s = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
            draw(this, s, color)
        }
    }
}

// ---------- Helper builders ----------

private fun line(x1: Float, y1: Float, x2: Float, y2: Float): Path = Path().apply {
    moveTo(x1, y1); lineTo(x2, y2)
}

private fun circle(cx: Float, cy: Float, r: Float): Path = Path().apply {
    addOval(Rect(cx - r, cy - r, cx + r, cy + r))
}

private fun rectPath(x: Float, y: Float, w: Float, h: Float, r: Float = 0f): Path = Path().apply {
    addRoundRect(
        androidx.compose.ui.geometry.RoundRect(
            left = x, top = y, right = x + w, bottom = y + h,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(r, r)
        )
    )
}

private fun polygon(vararg pts: Float): Path = Path().apply {
    moveTo(pts[0], pts[1])
    var i = 2
    while (i < pts.size) { lineTo(pts[i], pts[i + 1]); i += 2 }
    close()
}

// ---------- Standard icons ----------

@Composable
fun IconBack(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(Path().apply { moveTo(19f, 12f); lineTo(5f, 12f) }, c, style = s)
        drawPath(Path().apply { moveTo(12f, 19f); lineTo(5f, 12f); lineTo(12f, 5f) }, c, style = s)
    }

@Composable
fun IconSearch(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(circle(11f, 11f, 7f), c, style = s)
        drawPath(line(21f, 21f, 16.7f, 16.7f), c, style = s)
    }

@Composable
fun IconStar(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = polygon(
            12f, 2f, 15.09f, 8.26f, 22f, 9.27f, 17f, 14.14f, 18.18f, 21.02f,
            12f, 17.77f, 5.82f, 21.02f, 7f, 14.14f, 2f, 9.27f, 8.91f, 8.26f
        )
        drawPath(p, c)
        drawPath(p, c, style = s)
    }

@Composable
fun IconStarOutline(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = polygon(
            12f, 2f, 15.09f, 8.26f, 22f, 9.27f, 17f, 14.14f, 18.18f, 21.02f,
            12f, 17.77f, 5.82f, 21.02f, 7f, 14.14f, 2f, 9.27f, 8.91f, 8.26f
        )
        drawPath(p, c, style = s)
    }

@Composable
fun IconClock(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(circle(12f, 12f, 9f), c, style = s)
        drawPath(Path().apply { moveTo(12f, 7f); lineTo(12f, 12f); lineTo(15f, 14f) }, c, style = s)
    }

@Composable
fun IconPin(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        // Teardrop pin: top half-circle around (12,10) r=9, with two curves down to (12,23)
        val p = Path().apply {
            moveTo(21f, 10f)
            cubicTo(21f, 17f, 12f, 23f, 12f, 23f)
            cubicTo(12f, 23f, 3f, 17f, 3f, 10f)
            cubicTo(3f, 5f, 7f, 1f, 12f, 1f)
            cubicTo(17f, 1f, 21f, 5f, 21f, 10f)
            close()
        }
        drawPath(p, c, style = s)
        drawPath(circle(12f, 10f, 3f), c, style = s)
    }

@Composable
fun IconPhone(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = Path().apply {
            moveTo(22f, 16.92f); lineTo(22f, 19.92f)
            cubicTo(22f, 21f, 21.1f, 21.92f, 19.82f, 21.92f)
            cubicTo(16f, 21.7f, 13f, 20.5f, 11.19f, 18.85f)
            cubicTo(8.5f, 16.7f, 6.5f, 14.5f, 5.19f, 12.85f)
            cubicTo(3.5f, 10f, 2.5f, 6.5f, 2.12f, 4.18f)
            cubicTo(2f, 3f, 2.9f, 2f, 4.11f, 2f)
            lineTo(7.11f, 2f)
            cubicTo(8.1f, 2f, 9f, 2.7f, 9.11f, 3.72f)
            cubicTo(9.24f, 4.68f, 9.47f, 5.62f, 9.81f, 6.53f)
            cubicTo(10f, 7.2f, 9.85f, 7.95f, 9.36f, 8.64f)
            lineTo(8.09f, 9.91f)
            cubicTo(9.5f, 12.5f, 11.5f, 14.5f, 14.09f, 15.91f)
            lineTo(15.36f, 14.64f)
            cubicTo(16f, 14.15f, 16.8f, 14f, 17.47f, 14.19f)
            cubicTo(18.38f, 14.53f, 19.32f, 14.76f, 20.28f, 14.89f)
            cubicTo(21.3f, 15f, 22f, 15.92f, 22f, 16.92f)
            close()
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconChat(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = Path().apply {
            moveTo(21f, 11.5f)
            cubicTo(21f, 16.2f, 16.97f, 20f, 12f, 20f)
            cubicTo(10.7f, 20f, 9.4f, 19.7f, 8.2f, 19.1f)
            lineTo(3f, 21f)
            lineTo(4.9f, 15.8f)
            cubicTo(4f, 14.5f, 3f, 13f, 3f, 11.5f)
            cubicTo(3f, 6.8f, 7.03f, 3f, 12f, 3f)
            cubicTo(16.97f, 3f, 21f, 6.8f, 21f, 11.5f)
            close()
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconFilter(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(polygon(22f, 3f, 2f, 3f, 10f, 12.46f, 10f, 19f, 14f, 21f, 14f, 12.46f), c, style = s)
    }

@Composable
fun IconCheck(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { s, c ->
        drawPath(Path().apply { moveTo(20f, 6f); lineTo(9f, 17f); lineTo(4f, 12f) }, c, style = s)
    }

@Composable
fun IconX(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(line(18f, 6f, 6f, 18f), c, style = s)
        drawPath(line(6f, 6f, 18f, 18f), c, style = s)
    }

@Composable
fun IconChevron(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(Path().apply { moveTo(9f, 18f); lineTo(15f, 12f); lineTo(9f, 6f) }, c, style = s)
    }

@Composable
fun IconMap(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(polygon(1f, 6f, 1f, 22f, 8f, 18f, 16f, 22f, 23f, 18f, 23f, 2f, 16f, 6f, 8f, 2f), c, style = s)
        drawPath(line(8f, 2f, 8f, 18f), c, style = s)
        drawPath(line(16f, 6f, 16f, 22f), c, style = s)
    }

@Composable
fun IconShield(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = Path().apply {
            moveTo(12f, 22f)
            cubicTo(12f, 22f, 20f, 18f, 20f, 12f)
            lineTo(20f, 5f)
            lineTo(12f, 2f)
            lineTo(4f, 5f)
            lineTo(4f, 12f)
            cubicTo(4f, 18f, 12f, 22f, 12f, 22f)
            close()
        }
        drawPath(p, c, style = s)
        drawPath(Path().apply { moveTo(9f, 12f); lineTo(11f, 14f); lineTo(15f, 10f) }, c, style = s)
    }

@Composable
fun IconWrench(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { s, c ->
        val p = Path().apply {
            moveTo(14.7f, 6.3f)
            cubicTo(13.2f, 4.8f, 13.2f, 2.4f, 14.7f, 0.9f)
            lineTo(15.9f, 2.1f)
            cubicTo(15f, 3f, 15f, 4.5f, 15.9f, 5.4f)
            cubicTo(16.8f, 6.3f, 18.3f, 6.3f, 19.2f, 5.4f)
            lineTo(20.4f, 6.6f)
            cubicTo(18.9f, 8.1f, 16.5f, 8.1f, 15f, 6.6f)
            lineTo(5.4f, 16.2f)
            cubicTo(4.5f, 17.1f, 4.5f, 18.6f, 5.4f, 19.5f)
            cubicTo(6.3f, 20.4f, 7.8f, 20.4f, 8.7f, 19.5f)
            lineTo(18.3f, 9.9f)
            lineTo(19.5f, 11.1f)
            lineTo(9.9f, 20.7f)
            cubicTo(8.4f, 22.2f, 6f, 22.2f, 4.5f, 20.7f)
            cubicTo(3f, 19.2f, 3f, 16.8f, 4.5f, 15.3f)
            lineTo(14.1f, 5.7f)
            close()
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconUser(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(circle(12f, 8f, 4f), c, style = s)
        val p = Path().apply {
            moveTo(4f, 21f)
            cubicTo(4f, 16.5f, 7.5f, 14f, 12f, 14f)
            cubicTo(16.5f, 14f, 20f, 16.5f, 20f, 21f)
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconMail(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(rectPath(2f, 5f, 20f, 14f, 2f), c, style = s)
        drawPath(Path().apply { moveTo(2f, 7f); lineTo(12f, 13f); lineTo(22f, 7f) }, c, style = s)
    }

@Composable
fun IconLock(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(rectPath(4f, 11f, 16f, 10f, 2f), c, style = s)
        val p = Path().apply {
            moveTo(8f, 11f)
            lineTo(8f, 7f)
            cubicTo(8f, 4.8f, 9.8f, 3f, 12f, 3f)
            cubicTo(14.2f, 3f, 16f, 4.8f, 16f, 7f)
            lineTo(16f, 11f)
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconEye(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = Path().apply {
            moveTo(2f, 12f)
            cubicTo(2f, 12f, 6f, 5f, 12f, 5f)
            cubicTo(18f, 5f, 22f, 12f, 22f, 12f)
            cubicTo(22f, 12f, 18f, 19f, 12f, 19f)
            cubicTo(6f, 19f, 2f, 12f, 2f, 12f)
            close()
        }
        drawPath(p, c, style = s)
        drawPath(circle(12f, 12f, 3f), c, style = s)
    }

@Composable
fun IconEyeOff(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        val p = Path().apply {
            moveTo(2f, 12f)
            cubicTo(2f, 12f, 6f, 5f, 12f, 5f)
            cubicTo(18f, 5f, 22f, 12f, 22f, 12f)
            cubicTo(22f, 12f, 18f, 19f, 12f, 19f)
            cubicTo(6f, 19f, 2f, 12f, 2f, 12f)
            close()
        }
        drawPath(p, c, style = s)
        drawPath(line(3f, 3f, 21f, 21f), c, style = s)
    }

@Composable
fun IconHome(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { s, c ->
        val p = Path().apply {
            moveTo(3f, 11f)
            lineTo(12f, 3f)
            lineTo(21f, 11f)
            lineTo(21f, 21f)
            lineTo(14f, 21f)
            lineTo(14f, 14f)
            lineTo(10f, 14f)
            lineTo(10f, 21f)
            lineTo(3f, 21f)
            close()
        }
        drawPath(p, c, style = s)
    }

@Composable
fun IconShop(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { s, c ->
        // Shopping bag silhouette
        val p = Path().apply {
            moveTo(4f, 7f)
            lineTo(20f, 7f)
            lineTo(19f, 21f)
            lineTo(5f, 21f)
            close()
        }
        drawPath(p, c, style = s)
        val handle = Path().apply {
            moveTo(8f, 7f)
            lineTo(8f, 5f)
            cubicTo(8f, 3f, 9.8f, 2f, 12f, 2f)
            cubicTo(14.2f, 2f, 16f, 3f, 16f, 5f)
            lineTo(16f, 7f)
        }
        drawPath(handle, c, style = s)
    }

@Composable
fun IconCalendar(size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        drawPath(rectPath(3f, 4f, 18f, 18f, 2f), c, style = s)
        drawPath(line(16f, 2f, 16f, 6f), c, style = s)
        drawPath(line(8f, 2f, 8f, 6f), c, style = s)
        drawPath(line(3f, 10f, 21f, 10f), c, style = s)
    }

// ---------- Specialty icons ----------

@Composable
fun SpecIcon(id: String, size: Dp, color: Color, modifier: Modifier = Modifier) =
    LineIcon(size, color, modifier) { s, c ->
        when (id) {
            "engine" -> {
                drawPath(rectPath(4f, 9f, 14f, 9f, 1f), c, style = s)
                drawPath(Path().apply {
                    moveTo(8f, 9f); lineTo(8f, 6f); lineTo(14f, 6f); lineTo(14f, 9f)
                }, c, style = s)
                drawPath(line(18f, 12f, 20f, 12f), c, style = s)
                drawPath(line(20f, 12f, 20f, 15f), c, style = s)
                drawPath(line(20f, 15f, 18f, 15f), c, style = s)
                drawPath(line(4f, 12f, 2f, 12f), c, style = s)
                drawPath(line(9f, 18f, 9f, 20f), c, style = s)
                drawPath(line(13f, 18f, 13f, 20f), c, style = s)
            }
            "gearbox" -> {
                drawPath(circle(7f, 7f, 2.5f), c, style = s)
                drawPath(circle(17f, 7f, 2.5f), c, style = s)
                drawPath(circle(7f, 17f, 2.5f), c, style = s)
                drawPath(circle(17f, 17f, 2.5f), c, style = s)
                drawPath(line(7f, 9.5f, 7f, 14.5f), c, style = s)
                drawPath(line(17f, 9.5f, 17f, 14.5f), c, style = s)
                drawPath(line(9.5f, 7f, 14.5f, 7f), c, style = s)
                drawPath(line(9.5f, 17f, 14.5f, 17f), c, style = s)
            }
            "electrical" -> {
                drawPath(polygon(13f, 2f, 3f, 14f, 12f, 14f, 11f, 22f, 21f, 10f, 12f, 10f), c, style = s)
            }
            "radiator" -> {
                drawPath(rectPath(4f, 5f, 16f, 14f, 1f), c, style = s)
                drawPath(line(8f, 5f, 8f, 19f), c, style = s)
                drawPath(line(12f, 5f, 12f, 19f), c, style = s)
                drawPath(line(16f, 5f, 16f, 19f), c, style = s)
            }
            "brakes" -> {
                drawPath(circle(12f, 12f, 9f), c, style = s)
                drawPath(circle(12f, 12f, 3f), c, style = s)
                drawPath(line(12f, 3f, 12f, 6f), c, style = s)
                drawPath(line(12f, 18f, 12f, 21f), c, style = s)
                drawPath(line(3f, 12f, 6f, 12f), c, style = s)
                drawPath(line(18f, 12f, 21f, 12f), c, style = s)
            }
            "suspension" -> {
                drawPath(line(12f, 2f, 12f, 6f), c, style = s)
                drawPath(line(12f, 18f, 12f, 22f), c, style = s)
                drawPath(line(9f, 6f, 15f, 6f), c, style = s)
                drawPath(line(9f, 18f, 15f, 18f), c, style = s)
                drawPath(line(10f, 7f, 14f, 9f), c, style = s)
                drawPath(line(10f, 9f, 14f, 11f), c, style = s)
                drawPath(line(10f, 11f, 14f, 13f), c, style = s)
                drawPath(line(10f, 13f, 14f, 15f), c, style = s)
                drawPath(line(10f, 15f, 14f, 17f), c, style = s)
            }
            "exhaust" -> {
                val p = Path().apply {
                    moveTo(2f, 14f)
                    cubicTo(4f, 14f, 4f, 12f, 6f, 12f)
                    cubicTo(8f, 12f, 8f, 14f, 10f, 14f)
                    cubicTo(12f, 14f, 12f, 12f, 14f, 12f)
                    cubicTo(16f, 12f, 16f, 14f, 18f, 14f)
                }
                drawPath(p, c, style = s)
                drawPath(circle(20f, 10f, 2f), c, style = s)
                drawPath(line(3f, 18f, 17f, 18f), c, style = s)
            }
            "aircon" -> {
                drawPath(line(12f, 2f, 12f, 22f), c, style = s)
                drawPath(line(2f, 12f, 22f, 12f), c, style = s)
                drawPath(line(5f, 5f, 19f, 19f), c, style = s)
                drawPath(line(19f, 5f, 5f, 19f), c, style = s)
            }
            "bodywork" -> {
                drawPath(Path().apply {
                    moveTo(3f, 16f); lineTo(6f, 9f); lineTo(18f, 9f); lineTo(21f, 16f)
                }, c, style = s)
                drawPath(line(3f, 16f, 3f, 19f), c, style = s)
                drawPath(line(21f, 16f, 21f, 19f), c, style = s)
                drawPath(circle(7f, 17f, 1.5f), c, style = s)
                drawPath(circle(17f, 17f, 1.5f), c, style = s)
            }
            "tires" -> {
                drawPath(circle(12f, 12f, 9f), c, style = s)
                drawPath(circle(12f, 12f, 4f), c, style = s)
                drawPath(line(12f, 3f, 12f, 8f), c, style = s)
                drawPath(line(12f, 16f, 12f, 21f), c, style = s)
                drawPath(line(3f, 12f, 8f, 12f), c, style = s)
                drawPath(line(16f, 12f, 21f, 12f), c, style = s)
            }
            "diagnostic" -> {
                drawPath(rectPath(3f, 5f, 18f, 12f, 1f), c, style = s)
                drawPath(line(7f, 17f, 7f, 20f), c, style = s)
                drawPath(line(17f, 17f, 17f, 20f), c, style = s)
                drawPath(line(5f, 21f, 19f, 21f), c, style = s)
                drawPath(Path().apply {
                    moveTo(7f, 11f); lineTo(9f, 13f); lineTo(12f, 9f); lineTo(14f, 12f); lineTo(17f, 10f)
                }, c, style = s)
            }
            "ev" -> {
                drawPath(rectPath(3f, 8f, 14f, 8f, 1f), c, style = s)
                drawPath(Path().apply {
                    moveTo(17f, 10f); lineTo(19f, 10f); lineTo(19f, 14f); lineTo(17f, 14f)
                }, c, style = s)
                drawPath(Path().apply {
                    moveTo(9f, 10f); lineTo(8f, 12f); lineTo(10f, 12f); lineTo(9f, 14f)
                }, c, style = s)
            }
            else -> drawPath(circle(12f, 12f, 9f), c, style = s)
        }
    }

@Composable
fun IconSettings(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { st, c ->
        // Gear: 8 spokes around an inner ring
        drawPath(circle(12f, 12f, 3f), c, style = st)
        drawPath(circle(12f, 12f, 7.5f), c, style = st)
        for (i in 0 until 8) {
            val ang = (i * 45f) * (kotlin.math.PI.toFloat() / 180f)
            val cos = kotlin.math.cos(ang)
            val sin = kotlin.math.sin(ang)
            drawPath(
                line(12f + cos * 7.5f, 12f + sin * 7.5f, 12f + cos * 10f, 12f + sin * 10f),
                c,
                style = st,
            )
        }
    }

@Composable
fun IconSignOut(size: Dp, color: Color, modifier: Modifier = Modifier, stroke: Float = 1.75f) =
    LineIcon(size, color, modifier, stroke) { st, c ->
        // Door
        drawPath(Path().apply {
            moveTo(14f, 4f); lineTo(7f, 4f); lineTo(7f, 20f); lineTo(14f, 20f)
        }, c, style = st)
        // Arrow out
        drawPath(line(11f, 12f, 21f, 12f), c, style = st)
        drawPath(Path().apply {
            moveTo(17f, 8f); lineTo(21f, 12f); lineTo(17f, 16f)
        }, c, style = st)
    }
