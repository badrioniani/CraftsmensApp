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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.auth.AuthViewModel
import org.example.project.data.auth.UserDto
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AppLogo
import org.example.project.ui.components.IconButton40
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconCheck
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.theme.CraftsmenColors

/**
 * 6-digit OTP screen that mirrors the web's /verify-phone page:
 *  - sends an SMS on mount
 *  - 6 separate boxes with auto-advance + backspace-to-previous
 *  - resend button
 *  - success state auto-routes back to the home screen
 */
@Composable
fun VerifyPhoneScreen(
    theme: CraftsmenColors,
    authVm: AuthViewModel,
    user: UserDto?,
    onVerified: () -> Unit,
    onLogout: () -> Unit,
) {
    val s = LocalStrings.current
    val scope = rememberCoroutineScope()

    var sending by remember { mutableStateOf(false) }
    var codeSent by remember { mutableStateOf(false) }
    var verifying by remember { mutableStateOf(false) }
    var resendFlash by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val digits = remember { mutableStateListOf6() }

    fun sendCode() {
        if (sending) return
        sending = true
        error = null
        authVm.sendPhoneCode(
            onSuccess = {
                sending = false
                codeSent = true
            },
            onError = { msg ->
                sending = false
                error = msg
            },
        )
    }

    fun submit(joined: String) {
        if (verifying || joined.length != 6) return
        verifying = true
        error = null
        authVm.verifyPhone(
            code = joined,
            onSuccess = {
                verifying = false
                success = true
                scope.launch {
                    delay(1500)
                    onVerified()
                }
            },
            onError = { msg ->
                verifying = false
                error = msg
                for (i in digits.indices) digits[i] = ""
            },
        )
    }

    LaunchedEffect(Unit) { sendCode() }

    if (success) {
        Box(
            modifier = Modifier.fillMaxSize().background(theme.bg),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(theme.success.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center,
                ) {
                    IconCheck(size = 32.dp, color = theme.success, stroke = 2.4f)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = s.verifyPhoneSuccessTitle,
                    color = theme.text,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = s.verifyPhoneSuccessBody,
                    color = theme.textDim,
                    fontSize = 13.sp,
                )
            }
        }
        return
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
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppLogo(size = 32.dp)
                Spacer(Modifier.width(8.dp))
                Text(s.appName, color = theme.text, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            }
            IconButton40(theme = theme, onClick = onLogout) {
                IconBack(size = 16.dp, color = theme.text)
            }
        }

        Spacer(Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .clip(CircleShape)
                .background(theme.accentSoft),
            contentAlignment = Alignment.Center,
        ) {
            IconPhone(size = 28.dp, color = theme.accent)
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = s.verifyPhoneTitle,
            color = theme.text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val subtitle = when {
                sending && !codeSent -> s.verifyPhoneSubtitleSending
                codeSent -> s.verifyPhoneSubtitleSent
                else -> s.verifyPhoneSubtitleFallback
            }
            Text(
                text = subtitle,
                color = theme.textDim,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            val phone = user?.phone
            if (codeSent && !phone.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = phone,
                    color = theme.text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        OtpRow(
            theme = theme,
            digits = digits,
            disabled = verifying || sending,
            hasError = error != null,
            onComplete = { submit(it) },
        )

        if (error != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = error!!,
                color = theme.danger,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(Modifier.height(20.dp))

        AppButton(
            theme = theme,
            text = if (verifying) s.verifyPhoneVerifying else s.verifyPhoneAction,
            onClick = { submit(digits.joinToString("")) },
            enabled = digits.all { it.isNotEmpty() } && !verifying && !sending,
            busy = verifying,
        )

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(s.verifyPhoneResendQuestion, color = theme.textDim, fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            val resendLabel = when {
                resendFlash -> s.verifyPhoneResendSent
                sending -> s.verifyPhoneResendSending
                else -> s.verifyPhoneResendAction
            }
            Text(
                text = resendLabel,
                color = if (resendFlash) theme.success else theme.accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(enabled = !sending && !resendFlash) {
                        sendCode()
                        scope.launch {
                            resendFlash = true
                            delay(4000)
                            resendFlash = false
                        }
                    }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }

        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun OtpRow(
    theme: CraftsmenColors,
    digits: MutableList<String>,
    disabled: Boolean,
    hasError: Boolean,
    onComplete: (String) -> Unit,
) {
    val focusers = remember { List(6) { FocusRequester() } }
    LaunchedEffect(Unit) { focusers[0].requestFocus() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        for (index in 0 until 6) {
            OtpBox(
                theme = theme,
                value = digits[index],
                disabled = disabled,
                hasError = hasError,
                focusRequester = focusers[index],
                onChange = { incoming ->
                    val sanitized = incoming.filter { it.isDigit() }
                    when {
                        sanitized.isEmpty() -> digits[index] = ""
                        // Paste — distribute across boxes from this index onward.
                        sanitized.length > 1 -> {
                            var cursor = index
                            for (ch in sanitized) {
                                if (cursor >= 6) break
                                digits[cursor] = ch.toString()
                                cursor++
                            }
                            val target = (cursor.coerceAtMost(5))
                            focusers[target].requestFocus()
                            if (digits.all { it.isNotEmpty() }) onComplete(digits.joinToString(""))
                        }
                        else -> {
                            digits[index] = sanitized.last().toString()
                            if (index < 5) focusers[index + 1].requestFocus()
                            else if (digits.all { it.isNotEmpty() }) onComplete(digits.joinToString(""))
                        }
                    }
                },
                onBackspaceEmpty = {
                    if (index > 0) {
                        digits[index - 1] = ""
                        focusers[index - 1].requestFocus()
                    }
                },
            )
        }
    }
}

@Composable
private fun OtpBox(
    theme: CraftsmenColors,
    value: String,
    disabled: Boolean,
    hasError: Boolean,
    focusRequester: FocusRequester,
    onChange: (String) -> Unit,
    onBackspaceEmpty: () -> Unit,
) {
    val borderColor = when {
        hasError -> theme.danger
        value.isNotEmpty() -> theme.accent
        else -> theme.border
    }
    val tfv = remember(value) {
        TextFieldValue(text = value, selection = androidx.compose.ui.text.TextRange(value.length))
    }
    Box(
        modifier = Modifier
            .width(44.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(theme.bgInput)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = tfv,
            onValueChange = { new ->
                // Backspace on an already-empty box: kick focus back one step.
                if (new.text.isEmpty() && value.isEmpty()) {
                    onBackspaceEmpty()
                    return@BasicTextField
                }
                onChange(new.text)
            },
            enabled = !disabled,
            singleLine = true,
            textStyle = TextStyle(
                color = theme.text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            ),
            cursorBrush = SolidColor(theme.accent),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.focusRequester(focusRequester),
        )
    }
}

private fun mutableStateListOf6(): MutableList<String> =
    androidx.compose.runtime.mutableStateListOf("", "", "", "", "", "")
