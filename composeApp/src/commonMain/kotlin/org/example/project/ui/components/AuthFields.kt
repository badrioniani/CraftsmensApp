package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.icons.IconEye
import org.example.project.ui.icons.IconEyeOff
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun FieldLabel(text: String, theme: CraftsmenColors) {
    Text(
        text = text.uppercase(),
        color = theme.textDim,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.8.sp,
    )
}

@Composable
fun AuthTextField(
    theme: CraftsmenColors,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String? = null,
    leading: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    onImeAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val setInputFocused = LocalSetInputFocused.current
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            FieldLabel(label, theme)
            Spacer(Modifier.height(8.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(theme.bgInput)
                .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (leading != null) leading()
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = theme.textMute, fontSize = 15.sp) },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { setInputFocused(it.isFocused) },
                singleLine = true,
                visualTransformation = if (isPassword && !passwordVisible)
                    PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword && !passwordVisible) KeyboardType.Password else keyboardType,
                    imeAction = imeAction,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onImeAction?.invoke() },
                    onGo = { onImeAction?.invoke() },
                    onNext = { onImeAction?.invoke() },
                    onSearch = { onImeAction?.invoke() },
                    onSend = { onImeAction?.invoke() },
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = theme.accent,
                    focusedTextColor = theme.text,
                    unfocusedTextColor = theme.text,
                ),
            )
            if (isPassword) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { passwordVisible = !passwordVisible }
                        .padding(6.dp),
                ) {
                    if (passwordVisible) IconEyeOff(size = 18.dp, color = theme.textDim)
                    else IconEye(size = 18.dp, color = theme.textDim)
                }
            }
        }
    }
}
