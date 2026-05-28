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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.auth.UserDto
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconMail
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconSignOut
import org.example.project.ui.icons.IconUser
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun SettingsScreen(
    theme: CraftsmenColors,
    user: UserDto?,
    onLogout: () -> Unit,
    onOpenDashboard: (() -> Unit)? = null,
    onSignIn: (() -> Unit)? = null,
) {
    val s = LocalStrings.current
    var confirming by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
    ) {
        Spacer(Modifier.height(48.dp))
        Text(
            text = s.profileTitle,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )

        Spacer(Modifier.height(24.dp))
        SectionLabel(theme, s.profileAccountSection)

        Spacer(Modifier.height(10.dp))
        if (user == null) {
            GuestCard(theme)
            Spacer(Modifier.height(20.dp))
            AppButton(
                theme = theme,
                text = s.loginCta,
                onClick = { onSignIn?.invoke() },
            )
        } else {
            ProfileCard(theme = theme, user = user, roleLabel = s.profileRoleLabel) { roleKey ->
                s.profileRole(roleKey)
            }

            if (user.role == "mechanic" && onOpenDashboard != null) {
                Spacer(Modifier.height(20.dp))
                AppButton(
                    theme = theme,
                    text = s.dashTitle,
                    onClick = onOpenDashboard,
                    variant = ButtonVariant.Secondary,
                )
            }

            Spacer(Modifier.height(28.dp))
            AppButton(
                theme = theme,
                text = s.profileLogoutAction,
                onClick = { confirming = true },
                enabled = true,
            )
        }

        Spacer(Modifier.height(40.dp))
    }

    if (confirming) {
        ConfirmDialog(
            theme = theme,
            title = s.profileLogoutConfirmTitle,
            message = s.profileLogoutConfirmMessage,
            confirmText = s.profileLogoutAction,
            cancelText = s.cancel,
            onConfirm = {
                confirming = false
                onLogout()
            },
            onCancel = { confirming = false },
        )
    }
}

@Composable
private fun GuestCard(theme: CraftsmenColors) {
    val s = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(theme.accentSoft),
                contentAlignment = Alignment.Center,
            ) {
                IconUser(size = 22.dp, color = theme.accent)
            }
            Spacer(Modifier.size(12.dp))
            Text(
                text = s.guestModeTitle,
                color = theme.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            text = s.guestModeBody,
            color = theme.textDim,
            fontSize = 13.sp,
            lineHeight = 19.sp,
        )
    }
}

@Composable
private fun SectionLabel(theme: CraftsmenColors, text: String) {
    Text(
        text = text.uppercase(),
        color = theme.textMute,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.sp,
    )
}

@Composable
private fun ProfileCard(
    theme: CraftsmenColors,
    user: UserDto?,
    roleLabel: String,
    formatRole: (String) -> String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.bgCard)
            .border(1.dp, theme.border, RoundedCornerShape(16.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(theme.accentSoft),
                contentAlignment = Alignment.Center,
            ) {
                IconUser(size = 22.dp, color = theme.accent)
            }
            Spacer(Modifier.size(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = user?.name ?: "—",
                    color = theme.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = user?.let { formatRole(it.role) } ?: "",
                    color = theme.textDim,
                    fontSize = 13.sp,
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))

        InfoRow(theme = theme, label = "Email", value = user?.email ?: "") {
            IconMail(size = 16.dp, color = theme.textDim)
        }
        val phone = user?.phone
        if (!phone.isNullOrBlank()) {
            InfoRow(theme = theme, label = "Phone", value = phone) {
                IconPhone(size = 16.dp, color = theme.textDim)
            }
        }
        InfoRow(theme = theme, label = roleLabel, value = user?.let { formatRole(it.role) } ?: "") {
            IconSignOut(size = 16.dp, color = theme.textDim)
        }
    }
}

@Composable
private fun InfoRow(
    theme: CraftsmenColors,
    label: String,
    value: String,
    icon: @Composable () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(Modifier.size(10.dp))
        Column {
            Text(
                text = label,
                color = theme.textMute,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.6.sp,
            )
            Spacer(Modifier.size(2.dp))
            Text(
                text = value,
                color = theme.text,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
private fun ConfirmDialog(
    theme: CraftsmenColors,
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onCancel),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(theme.bgCard)
                .border(1.dp, theme.border, RoundedCornerShape(20.dp))
                .clickable(enabled = false, onClick = {})
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = title,
                color = theme.text,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = message,
                color = theme.textDim,
                fontSize = 14.sp,
            )
            Spacer(Modifier.size(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, theme.borderStrong, RoundedCornerShape(12.dp))
                        .clickable(onClick = onCancel)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = cancelText,
                        color = theme.text,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(theme.danger)
                        .clickable(onClick = onConfirm)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = confirmText,
                        color = androidx.compose.ui.graphics.Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}
