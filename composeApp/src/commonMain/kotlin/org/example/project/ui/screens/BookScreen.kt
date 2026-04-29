package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.CarBrand
import org.example.project.data.Mechanic
import org.example.project.data.Specialty
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.components.Section
import org.example.project.ui.components.StickyBottom
import androidx.compose.ui.focus.onFocusChanged
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.SpecIcon
import org.example.project.ui.theme.CraftsmenColors

private data class BookDay(val d: Int, val day: String, val date: String, val month: String, val avail: Boolean)

@Composable
fun BookScreen(
    theme: CraftsmenColors,
    brand: CarBrand,
    spec: Specialty,
    mech: Mechanic,
    onBack: () -> Unit,
    onConfirm: () -> Unit,
) {
    val s = LocalStrings.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val setInputFocused = LocalSetInputFocused.current
    var date by remember { mutableStateOf(1) }
    var slot by remember { mutableStateOf<String?>(null) }
    var issue by remember { mutableStateOf("") }

    val days = listOf(
        BookDay(1, "WED", "29", "APR", true),
        BookDay(2, "THU", "30", "APR", true),
        BookDay(3, "FRI", "01", "MAY", false),
        BookDay(4, "SAT", "02", "MAY", true),
        BookDay(5, "SUN", "03", "MAY", false),
        BookDay(6, "MON", "04", "MAY", true),
    )
    val slots = listOf("08:00", "09:30", "11:00", "13:30", "15:00", "16:30")

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        ScreenHeader(theme, s.bookSlot, mech.name.uppercase(), onBack = onBack)
        LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(bottom = 16.dp)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgCard)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(theme.bgInput),
                        contentAlignment = Alignment.Center,
                    ) { SpecIcon(id = spec.id, size = 20.dp, color = theme.text) }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(s.specialtyName(spec.id), color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        val low = 50 + spec.id.length * 8
                        val high = 120 + spec.id.length * 12
                        Text(
                            "${s.estPrefix} \$$low–\$$high · ${s.diagnosticSuffix}",
                            color = theme.textDim,
                            fontSize = 11.sp,
                        )
                    }
                }
            }
            item {
                Section(theme, s.pickDate) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(days) { d ->
                            val sel = date == d.d
                            Column(
                                modifier = Modifier
                                    .width(60.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (sel) theme.accent else theme.bgCard)
                                    .border(
                                        1.5.dp,
                                        if (sel) theme.accent else theme.border,
                                        RoundedCornerShape(12.dp),
                                    )
                                    .clickable(enabled = d.avail) { if (d.avail) date = d.d }
                                    .padding(vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                Text(
                                    text = d.day,
                                    color = if (sel) theme.accentText else theme.textDim,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = d.date,
                                    color = if (sel) theme.accentText else theme.text.copy(alpha = if (d.avail) 1f else 0.4f),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    text = d.month,
                                    color = if (sel) theme.accentText else theme.textMute,
                                    fontSize = 9.sp,
                                )
                            }
                        }
                    }
                }
            }
            item {
                Section(theme, s.pickTime) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        slots.chunked(3).forEach { row ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                row.forEachIndexed { idx, s ->
                                    val taken = (slots.indexOf(s) == 1) || (slots.indexOf(s) == 4)
                                    val sel = slot == s
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .heightIn(min = 44.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (sel) theme.accent else theme.bgCard)
                                            .border(
                                                1.5.dp,
                                                if (sel) theme.accent else theme.border,
                                                RoundedCornerShape(12.dp),
                                            )
                                            .clickable(enabled = !taken) { if (!taken) slot = s }
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = s,
                                            color = when {
                                                sel -> theme.accentText
                                                taken -> theme.textMute
                                                else -> theme.text
                                            },
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textDecoration = if (taken) TextDecoration.LineThrough else null,
                                        )
                                    }
                                }
                                // Pad so unfilled cells maintain layout — last row is full
                                repeat(3 - row.size) { Spacer(modifier = Modifier.weight(1f)) }
                            }
                        }
                    }
                }
            }
            item {
                Section(theme, s.describeIssue) {
                    TextField(
                        value = issue,
                        onValueChange = { issue = it },
                        placeholder = {
                            Text(
                                s.issuePlaceholder,
                                color = theme.textDim,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp)
                            .onFocusChanged { setInputFocused(it.isFocused) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = theme.bgInput,
                            unfocusedContainerColor = theme.bgInput,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = theme.accent,
                            focusedTextColor = theme.text,
                            unfocusedTextColor = theme.text,
                        ),
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
        StickyBottom(theme) {
            val sel = slot
            val day = days.firstOrNull { it.d == date }
            AppButton(
                theme = theme,
                text = if (sel != null && day != null) s.confirmAt(day.day, sel) else s.pickTimeSlot,
                onClick = { if (sel != null) onConfirm() },
                enabled = sel != null,
            )
        }
    }
}
