package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconHome
import org.example.project.ui.icons.IconSettings
import org.example.project.ui.icons.IconShop
import org.example.project.ui.theme.CraftsmenColors

enum class MainTab { Home, Shop, Settings }

@Composable
fun BottomNavBar(
    theme: CraftsmenColors,
    selected: MainTab,
    onSelect: (MainTab) -> Unit,
) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.bgRaised),
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavItem(
                theme = theme,
                label = s.tabHome,
                active = selected == MainTab.Home,
                onClick = { onSelect(MainTab.Home) },
                icon = { color -> IconHome(size = 22.dp, color = color, stroke = 2f) },
                modifier = Modifier.weight(1f),
            )
            NavItem(
                theme = theme,
                label = s.tabShop,
                active = selected == MainTab.Shop,
                onClick = { onSelect(MainTab.Shop) },
                icon = { color -> IconShop(size = 22.dp, color = color, stroke = 2f) },
                modifier = Modifier.weight(1f),
            )
            NavItem(
                theme = theme,
                label = s.tabSettings,
                active = selected == MainTab.Settings,
                onClick = { onSelect(MainTab.Settings) },
                icon = { color -> IconSettings(size = 22.dp, color = color, stroke = 2f) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun NavItem(
    theme: CraftsmenColors,
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    icon: @Composable (color: androidx.compose.ui.graphics.Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bg = if (active) theme.accentSoft else androidx.compose.ui.graphics.Color.Transparent
    val fg = if (active) theme.accent else theme.textDim
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        icon(fg)
        if (active) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                color = fg,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
