package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AuthTextField
import org.example.project.ui.components.IconButton40
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconLock
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ResetPasswordScreen(
    theme: CraftsmenColors,
    email: String,
    prefilledCode: String?,
    onBack: () -> Unit,
    onConfirm: (email: String, code: String, newPassword: String) -> Unit,
    busy: Boolean = false,
    errorText: String? = null,
) {
    val s = LocalStrings.current
    var code by remember { mutableStateOf(prefilledCode.orEmpty()) }
    var newPassword by remember { mutableStateOf("") }
    val canSubmit = code.length == 6 && newPassword.length >= 8 && !busy

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
        }

        Spacer(Modifier.height(28.dp))
        Text(
            text = s.resetTitle,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = s.resetSubtitle,
            color = theme.textDim,
            fontSize = 14.sp,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = email,
            color = theme.textDim,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
        )

        Spacer(Modifier.height(28.dp))
        AuthTextField(
            theme = theme,
            value = code,
            onValueChange = { input -> code = input.filter { it.isDigit() }.take(6) },
            placeholder = s.resetCodePlaceholder,
            label = s.resetCodeLabel,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        )

        if (!prefilledCode.isNullOrBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = s.resetDemoNote,
                color = theme.textMute,
                fontSize = 11.sp,
            )
        }

        Spacer(Modifier.height(16.dp))
        AuthTextField(
            theme = theme,
            value = newPassword,
            onValueChange = { newPassword = it },
            placeholder = s.resetNewPasswordPlaceholder,
            label = s.resetNewPasswordLabel,
            leading = { IconLock(size = 18.dp, color = theme.textDim) },
            isPassword = true,
            imeAction = ImeAction.Done,
            onImeAction = { if (canSubmit) onConfirm(email, code, newPassword) },
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
        AppButton(
            theme = theme,
            text = if (busy) "..." else s.resetConfirmAction,
            onClick = { onConfirm(email, code, newPassword) },
            enabled = canSubmit,
        )

        Spacer(Modifier.height(40.dp))
    }
}
