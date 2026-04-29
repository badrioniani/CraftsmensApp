package org.example.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CraftsmenColors(
    val bg: Color,
    val bgRaised: Color,
    val bgCard: Color,
    val bgInput: Color,
    val border: Color,
    val borderStrong: Color,
    val text: Color,
    val textDim: Color,
    val textMute: Color,
    val accent: Color,
    val accentSoft: Color,
    val accentText: Color,
    val success: Color,
    val danger: Color,
    val isDark: Boolean,
    val statusFg: Color,
    val navHandle: Color,
    val frameBorder: Color,
    val stageBg: Color,
)

private fun Color.softCopy(alpha: Float): Color = copy(alpha = alpha)

fun buildTheme(dark: Boolean, accent: Color): CraftsmenColors {
    return if (dark) {
        CraftsmenColors(
            bg = Color(0xFF0E0E0E),
            bgRaised = Color(0xFF171717),
            bgCard = Color(0xFF1C1C1C),
            bgInput = Color(0xFF1A1A1A),
            border = Color(0xFF2A2A2A),
            borderStrong = Color(0xFF3A3A3A),
            text = Color(0xFFF5F5F4),
            textDim = Color(0xFFA8A29E),
            textMute = Color(0xFF78716C),
            accent = accent,
            accentSoft = accent.softCopy(0.13f),
            accentText = Color(0xFF0E0E0E),
            success = Color(0xFF84CC16),
            danger = Color(0xFFEF4444),
            isDark = true,
            statusFg = Color.White,
            navHandle = Color.White,
            frameBorder = Color(0xFF2A2A2A),
            stageBg = Color(0xFF050505),
        )
    } else {
        CraftsmenColors(
            bg = Color(0xFFFAFAF7),
            bgRaised = Color(0xFFFFFFFF),
            bgCard = Color(0xFFFFFFFF),
            bgInput = Color(0xFFF3F1ED),
            border = Color(0xFFE7E5E0),
            borderStrong = Color(0xFFD6D3CE),
            text = Color(0xFF171717),
            textDim = Color(0xFF57534E),
            textMute = Color(0xFFA8A29E),
            accent = accent,
            accentSoft = accent.softCopy(0.12f),
            accentText = Color(0xFF0E0E0E),
            success = Color(0xFF65A30D),
            danger = Color(0xFFDC2626),
            isDark = false,
            statusFg = Color(0xFF171717),
            navHandle = Color(0xFF171717),
            frameBorder = Color(0xFFD6D3CE),
            stageBg = Color(0xFFE8E5DD),
        )
    }
}

data class AccentOption(val id: String, val name: String, val color: Color)

val ACCENT_OPTIONS = listOf(
    AccentOption("amber", "Amber", Color(0xFFF5A524)),
    AccentOption("rust", "Rust", Color(0xFFE07033)),
    AccentOption("lime", "Lime", Color(0xFFA3E635)),
    AccentOption("azure", "Azure", Color(0xFF3B82F6)),
    AccentOption("rose", "Rose", Color(0xFFF43F5E)),
    AccentOption("violet", "Violet", Color(0xFFA78BFA)),
)

val LocalCraftsmenColors = staticCompositionLocalOf<CraftsmenColors> {
    error("No CraftsmenColors provided")
}

object CraftsmenTheme {
    val colors: CraftsmenColors
        @Composable
        get() = LocalCraftsmenColors.current
}
