package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Mechanic
import org.example.project.ui.components.AppButton
import org.example.project.ui.icons.IconCheck
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ConfirmScreen(theme: CraftsmenColors, mech: Mechanic, onDone: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(theme.bg)
            .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(theme.accentSoft)
                .border(2.dp, theme.accent, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            IconCheck(size = 42.dp, color = theme.accent, stroke = 2.5f)
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = "You're booked.",
            color = theme.text,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "${mech.name} will see you Wednesday at 11:00 AM. We sent a confirmation to your phone.",
            color = theme.textDim,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 21.sp,
            modifier = Modifier.widthIn(max = 280.dp),
        )

        Spacer(Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(theme.bgCard)
                .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(mech.photo),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = mech.initials,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column {
                    Text(text = mech.name, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "WED 29 APR · 11:00 AM", color = theme.textDim, fontSize = 11.sp)
                }
            }
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border).padding(vertical = 12.dp))
            Spacer(Modifier.height(12.dp))
            Text(
                text = "${mech.address} · ${mech.phone}",
                color = theme.textDim,
                fontSize = 12.sp,
                lineHeight = 18.sp,
            )
        }
        Spacer(Modifier.height(28.dp))
        AppButton(
            theme = theme,
            text = "Back to home",
            onClick = onDone,
            modifier = Modifier.widthIn(max = 320.dp),
        )
    }
}
