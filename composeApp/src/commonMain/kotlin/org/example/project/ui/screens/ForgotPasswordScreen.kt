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
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.theme.CraftsmenColors
import org.example.project.ui.util.isValidGeorgianPhone

@Composable
fun ForgotPasswordScreen(
    theme: CraftsmenColors,
    onBack: () -> Unit,
    onSendCode: (phone: String) -> Unit,
    busy: Boolean = false,
    errorText: String? = null,
) {
    val s = LocalStrings.current
    var phone by remember { mutableStateOf("") }
    val phoneValid = isValidGeorgianPhone(phone)
    val canSubmit = phoneValid && !busy

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
            text = s.forgotTitle,
            color = theme.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = s.forgotSubtitle,
            color = theme.textDim,
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(28.dp))
        AuthTextField(
            theme = theme,
            value = phone,
            onValueChange = { phone = it },
            placeholder = s.phonePlaceholder,
            label = s.phoneLabel,
            leading = { IconPhone(size = 18.dp, color = theme.textDim) },
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            onImeAction = { if (canSubmit) onSendCode(phone) },
        )

        if (phone.isNotBlank() && !phoneValid) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = s.phoneInvalid,
                color = theme.danger,
                fontSize = 12.sp,
            )
        }

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
            text = s.forgotSendCodeAction,
            onClick = { onSendCode(phone) },
            enabled = canSubmit,
            busy = busy,
        )
    }
}
