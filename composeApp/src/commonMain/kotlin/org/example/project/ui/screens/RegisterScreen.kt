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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.nav.UserRole
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AuthTextField
import org.example.project.ui.components.IconButton40
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconLock
import org.example.project.ui.icons.IconMail
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconUser
import org.example.project.ui.icons.IconWrench
import org.example.project.ui.theme.CraftsmenColors

data class RegisterFormResult(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: UserRole,
)

private val EmailRegex = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")

/** Friendly password-strength heuristic mirroring the web app. Returns 0–4. */
private fun scorePassword(pw: String): Int {
    if (pw.isEmpty()) return 0
    var score = 0
    if (pw.length >= 8) score++
    if (pw.length >= 12) score++
    if (pw.any { it.isLowerCase() } && pw.any { it.isUpperCase() }) score++
    if (pw.any { it.isDigit() } && pw.any { !it.isLetterOrDigit() }) score++
    return minOf(score, 4)
}

@Composable
fun RegisterScreen(
    theme: CraftsmenColors,
    role: UserRole,
    onBack: () -> Unit,
    onSubmit: (RegisterFormResult) -> Unit,
    onLogin: () -> Unit,
    busy: Boolean = false,
    errorText: String? = null,
) {
    val s = LocalStrings.current
    val isMechanic = role == UserRole.Mechanic

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailTouched by remember { mutableStateOf(false) }

    val emailValid = EmailRegex.matches(email.trim())
    val score = scorePassword(password)
    val showEmailError = emailTouched && email.isNotBlank() && !emailValid

    val canSubmit = fullName.isNotBlank() && emailValid && password.length >= 8 && !busy

    val submit: () -> Unit = {
        if (canSubmit) onSubmit(
            RegisterFormResult(
                name = fullName,
                email = email,
                phone = phone,
                password = password,
                role = role,
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton40(theme = theme, onClick = onBack) {
                IconBack(size = 20.dp, color = theme.text)
            }
            Spacer(Modifier.weight(1f))
            // Role badge
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(theme.accentSoft)
                    .border(1.dp, theme.accent, CircleShape)
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                if (isMechanic) IconWrench(size = 14.dp, color = theme.accent, stroke = 2f)
                else IconUser(size = 14.dp, color = theme.accent)
                Text(
                    text = if (isMechanic) s.roleMechanicTitle else s.roleUserTitle,
                    color = theme.accent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = if (isMechanic) s.registerMechanicTitle else s.registerUserTitle,
            color = theme.text,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 30.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (isMechanic) s.registerMechanicSubtitle else s.registerUserSubtitle,
            color = theme.textDim,
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(28.dp))

        AuthTextField(
            theme = theme,
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = s.fullNamePlaceholder,
            label = s.fullNameLabel,
            leading = { IconUser(size = 18.dp, color = theme.textDim) },
            imeAction = ImeAction.Next,
        )

        Spacer(Modifier.height(14.dp))
        AuthTextField(
            theme = theme,
            value = email,
            onValueChange = { email = it; emailTouched = true },
            placeholder = s.emailPlaceholder,
            label = s.emailLabel,
            leading = { IconMail(size = 18.dp, color = theme.textDim) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )
        if (showEmailError) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = s.emailInvalid,
                color = theme.danger,
                fontSize = 12.sp,
            )
        }

        Spacer(Modifier.height(14.dp))
        AuthTextField(
            theme = theme,
            value = phone,
            onValueChange = { phone = it },
            placeholder = s.phonePlaceholder,
            label = s.phoneLabel,
            leading = { IconPhone(size = 18.dp, color = theme.textDim) },
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next,
        )

        Spacer(Modifier.height(14.dp))
        AuthTextField(
            theme = theme,
            value = password,
            onValueChange = { password = it },
            placeholder = s.passwordPlaceholder,
            label = s.passwordLabel,
            leading = { IconLock(size = 18.dp, color = theme.textDim) },
            isPassword = true,
            imeAction = ImeAction.Done,
            onImeAction = { submit() },
        )

        Spacer(Modifier.height(10.dp))
        if (password.isEmpty()) {
            Text(
                text = s.passwordHint,
                color = theme.textMute,
                fontSize = 12.sp,
            )
        } else {
            PasswordStrengthMeter(theme = theme, score = score, strings = s)
        }

        if (!errorText.isNullOrBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = errorText,
                color = theme.danger,
                fontSize = 13.sp,
            )
        }

        Spacer(Modifier.height(24.dp))
        AppButton(
            theme = theme,
            text = s.createAccountAction,
            onClick = submit,
            enabled = canSubmit,
            busy = busy,
        )

        Spacer(Modifier.height(14.dp))
        Text(
            text = s.registerTermsNotice,
            color = theme.textMute,
            fontSize = 12.sp,
            lineHeight = 17.sp,
        )

        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
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
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun PasswordStrengthMeter(
    theme: CraftsmenColors,
    score: Int,
    strings: org.example.project.ui.i18n.Strings,
) {
    // weak → fair → good → strong, gradient from danger to success.
    val activeColor = when (score) {
        0, 1 -> theme.danger
        2 -> Color(0xFFF59E0B) // amber
        3 -> Color(0xFF84CC16) // lime
        else -> theme.success
    }
    val label = when {
        score <= 1 -> strings.pwStrengthWeak
        score == 2 -> strings.pwStrengthFair
        score == 3 -> strings.pwStrengthGood
        else -> strings.pwStrengthStrong
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            for (i in 1..4) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(if (i <= score) activeColor else theme.border),
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            color = theme.textDim,
            fontSize = 12.sp,
        )
    }
}
