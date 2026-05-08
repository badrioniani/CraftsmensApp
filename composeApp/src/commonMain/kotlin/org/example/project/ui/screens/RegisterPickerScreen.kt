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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.nav.UserRole
import org.example.project.ui.components.IconButton40
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconChevron
import org.example.project.ui.icons.IconUser
import org.example.project.ui.icons.IconWrench
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun RegisterPickerScreen(
    theme: CraftsmenColors,
    onBack: () -> Unit,
    onPickRole: (UserRole) -> Unit,
    onLogin: () -> Unit,
) {
    val s = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bg)
            .padding(horizontal = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton40(theme = theme, onClick = onBack) {
                IconBack(size = 20.dp, color = theme.text)
            }
        }

        Spacer(Modifier.height(28.dp))
        Text(
            text = s.registerPickerTitle,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = s.registerPickerSubtitle,
            color = theme.textDim,
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(32.dp))

        RoleCard(
            theme = theme,
            title = s.roleUserTitle,
            subtitle = s.roleUserSubtitle,
            onClick = { onPickRole(UserRole.User) },
            iconContent = { IconUser(size = 24.dp, color = theme.accentText) },
        )

        Spacer(Modifier.height(14.dp))

        RoleCard(
            theme = theme,
            title = s.roleMechanicTitle,
            subtitle = s.roleMechanicSubtitle,
            onClick = { onPickRole(UserRole.Mechanic) },
            iconContent = { IconWrench(size = 24.dp, color = theme.accentText, stroke = 2f) },
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = s.haveAccountQuestion,
                color = theme.textDim,
                fontSize = 14.sp,
            )
            Spacer(Modifier.size(6.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onLogin() }
                    .padding(horizontal = 4.dp, vertical = 4.dp),
            ) {
                Text(
                    text = s.loginCta,
                    color = theme.accent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun RoleCard(
    theme: CraftsmenColors,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(theme.accent),
            contentAlignment = Alignment.Center,
        ) { iconContent() }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = theme.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = theme.textDim,
                fontSize = 13.sp,
                lineHeight = 17.sp,
            )
        }

        IconChevron(size = 20.dp, color = theme.textMute)
    }
}
