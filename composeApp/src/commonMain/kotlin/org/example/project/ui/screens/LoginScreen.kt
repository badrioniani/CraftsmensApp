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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AppLogo
import org.example.project.ui.components.AuthTextField
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.icons.IconLock
import org.example.project.ui.icons.IconMail
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun LoginScreen(
    theme: CraftsmenColors,
    onLogin: (email: String, password: String) -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    busy: Boolean = false,
    errorText: String? = null,
) {
    val s = LocalStrings.current
    val lang = LocalLanguage.current
    val toggleLang = LocalToggleLanguage.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val canSubmit = email.isNotBlank() && password.length >= 4 && !busy

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
    ) {
        // Top bar with brand + lang
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppLogo(theme = theme, size = 32.dp)
                Spacer(Modifier.size(8.dp))
                Text(
                    text = s.appName,
                    color = theme.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, CircleShape)
                    .clickable { toggleLang() }
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = lang.display,
                    color = theme.text,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
                Box(modifier = Modifier.size(width = 1.dp, height = 12.dp).background(theme.border))
                Text(
                    text = if (lang.code == "en") "KA" else "EN",
                    color = theme.textMute,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                )
            }
        }

        Spacer(Modifier.height(48.dp))
        Text(
            text = s.loginTitle,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = s.loginSubtitle,
            color = theme.textDim,
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(28.dp))
        AuthTextField(
            theme = theme,
            value = email,
            onValueChange = { email = it },
            placeholder = s.emailPlaceholder,
            label = s.emailLabel,
            leading = { IconMail(size = 18.dp, color = theme.textDim) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )

        Spacer(Modifier.height(16.dp))
        AuthTextField(
            theme = theme,
            value = password,
            onValueChange = { password = it },
            placeholder = s.passwordPlaceholder,
            label = s.passwordLabel,
            leading = { IconLock(size = 18.dp, color = theme.textDim) },
            isPassword = true,
            imeAction = ImeAction.Done,
            onImeAction = { if (canSubmit) onLogin(email, password) },
        )

        if (!errorText.isNullOrBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = errorText,
                color = theme.danger,
                fontSize = 13.sp,
            )
        }

        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clip(CircleShape)
                .clickable(onClick = onForgotPassword)
                .padding(horizontal = 4.dp, vertical = 6.dp),
        ) {
            Text(
                text = s.forgotPassword,
                color = theme.accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }

        Spacer(Modifier.height(20.dp))
        AppButton(
            theme = theme,
            text = if (busy) "..." else s.loginAction,
            onClick = { onLogin(email, password) },
            enabled = canSubmit,
        )

        Spacer(Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(modifier = Modifier.weight(1f).height(1.dp).background(theme.border))
            Text(
                text = s.orDivider,
                color = theme.textMute,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
            )
            Box(modifier = Modifier.weight(1f).height(1.dp).background(theme.border))
        }

        Spacer(Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = s.noAccountQuestion,
                color = theme.textDim,
                fontSize = 14.sp,
            )
            Spacer(Modifier.size(6.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onRegister() }
                    .padding(horizontal = 4.dp, vertical = 4.dp),
            ) {
                Text(
                    text = s.registerCta,
                    color = theme.accent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}
