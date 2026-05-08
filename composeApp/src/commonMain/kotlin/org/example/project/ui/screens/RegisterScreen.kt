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
import org.example.project.ui.icons.IconCheck
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
    var confirmPassword by remember { mutableStateOf("") }

    var workshop by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    var agree by remember { mutableStateOf(false) }

    val baseValid = fullName.isNotBlank()
        && email.isNotBlank()
        && phone.isNotBlank()
        && password.length >= 8
        && password == confirmPassword
        && agree
        && !busy
    val canSubmit = if (isMechanic) {
        baseValid && workshop.isNotBlank() && specialty.isNotBlank() && city.isNotBlank()
    } else baseValid

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
            onValueChange = { email = it },
            placeholder = s.emailPlaceholder,
            label = s.emailLabel,
            leading = { IconMail(size = 18.dp, color = theme.textDim) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )

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

        if (isMechanic) {
            Spacer(Modifier.height(14.dp))
            AuthTextField(
                theme = theme,
                value = workshop,
                onValueChange = { workshop = it },
                placeholder = s.workshopNamePlaceholder,
                label = s.workshopNameLabel,
                leading = { IconWrench(size = 18.dp, color = theme.textDim, stroke = 2f) },
                imeAction = ImeAction.Next,
            )

            Spacer(Modifier.height(14.dp))
            AuthTextField(
                theme = theme,
                value = specialty,
                onValueChange = { specialty = it },
                placeholder = s.specialtyPlaceholder,
                label = s.specialtyLabel,
                imeAction = ImeAction.Next,
            )

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    AuthTextField(
                        theme = theme,
                        value = experience,
                        onValueChange = { v -> experience = v.filter { it.isDigit() } },
                        placeholder = s.experiencePlaceholder,
                        label = s.experienceLabel,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )
                }
                Box(modifier = Modifier.weight(1.4f)) {
                    AuthTextField(
                        theme = theme,
                        value = city,
                        onValueChange = { city = it },
                        placeholder = s.cityPlaceholder,
                        label = s.cityLabel,
                        imeAction = ImeAction.Next,
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        AuthTextField(
            theme = theme,
            value = password,
            onValueChange = { password = it },
            placeholder = s.passwordPlaceholder,
            label = s.passwordLabel,
            leading = { IconLock(size = 18.dp, color = theme.textDim) },
            isPassword = true,
            imeAction = ImeAction.Next,
        )

        Spacer(Modifier.height(14.dp))
        AuthTextField(
            theme = theme,
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = s.confirmPasswordPlaceholder,
            label = s.confirmPasswordLabel,
            leading = { IconLock(size = 18.dp, color = theme.textDim) },
            isPassword = true,
            imeAction = ImeAction.Done,
            onImeAction = { submit() },
        )

        if (!errorText.isNullOrBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = errorText,
                color = theme.danger,
                fontSize = 13.sp,
            )
        }

        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (agree) theme.accent else theme.bgRaised)
                    .border(1.dp, if (agree) theme.accent else theme.borderStrong, RoundedCornerShape(6.dp))
                    .clickable { agree = !agree },
                contentAlignment = Alignment.Center,
            ) {
                if (agree) IconCheck(size = 14.dp, color = theme.accentText, stroke = 2.5f)
            }
            Row(
                modifier = Modifier.clickable { agree = !agree },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = s.agreeTermsPrefix,
                    color = theme.textDim,
                    fontSize = 13.sp,
                )
                Text(
                    text = s.agreeTermsLink,
                    color = theme.accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        AppButton(
            theme = theme,
            text = if (busy) "..." else s.createAccountAction,
            onClick = submit,
            enabled = canSubmit,
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
