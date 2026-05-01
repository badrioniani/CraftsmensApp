package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.nav.Route
import org.example.project.ui.screens.BookScreen
import org.example.project.ui.screens.ConfirmScreen
import org.example.project.ui.screens.DetailScreen
import org.example.project.ui.screens.HomeScreen
import org.example.project.ui.screens.ListLayout
import org.example.project.ui.screens.ListScreen
import org.example.project.ui.screens.MapScreen
import org.example.project.ui.screens.ReviewsScreen
import org.example.project.ui.screens.SpecialtyScreen
import org.example.project.ui.i18n.AppLanguage
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.i18n.stringsFor
import org.example.project.ui.theme.ACCENT_OPTIONS
import org.example.project.ui.theme.LocalCraftsmenColors
import org.example.project.ui.theme.buildTheme

@Composable
@Preview
fun App() {
    val accent = remember { ACCENT_OPTIONS.first().color }
    val dark = isSystemInDarkTheme()
    val layout by remember { mutableStateOf(ListLayout.List) }
    val theme = remember(dark, accent) { buildTheme(dark, accent) }

    var language by remember { mutableStateOf(AppLanguage.EN) }
    val strings = remember(language) { stringsFor(language) }
    val toggleLanguage: () -> Unit = {
        language = if (language == AppLanguage.EN) AppLanguage.KA else AppLanguage.EN
    }

    var inputFocused by remember { mutableStateOf(false) }
    val setInputFocused: (Boolean) -> Unit = { inputFocused = it }

    val stack = remember { mutableStateListOf<Route>(Route.Home) }
    fun push(r: Route) { stack.add(r) }
    fun pop() { if (stack.size > 1) stack.removeAt(stack.lastIndex) }
    fun reset() { stack.clear(); stack.add(Route.Home) }
    fun replace(r: Route) {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)
        stack.add(r)
    }

    MaterialTheme {
        CompositionLocalProvider(
            LocalCraftsmenColors provides theme,
            LocalStrings provides strings,
            LocalLanguage provides language,
            LocalToggleLanguage provides toggleLanguage,
            LocalSetInputFocused provides setInputFocused,
        ) {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(theme.bg)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    },
            ) {
                when (val top = stack.last()) {
                    is Route.Home -> HomeScreen(
                        theme = theme,
                        onPickBrand = { brand -> push(Route.SpecPicker(brand)) },
                    )
                    is Route.SpecPicker -> SpecialtyScreen(
                        theme = theme,
                        brand = top.brand,
                        onBack = ::pop,
                        onPickSpec = { s -> push(Route.MechList(top.brand, s)) },
                    )
                    is Route.MechList -> ListScreen(
                        theme = theme,
                        brand = top.brand,
                        spec = top.spec,
                        layout = layout,
                        onBack = ::pop,
                        onPickMech = { m -> push(Route.Detail(top.brand, top.spec, m)) },
                        onMap = { push(Route.MapView(top.brand, top.spec)) },
                    )
                    is Route.MapView -> MapScreen(
                        theme = theme,
                        brand = top.brand,
                        spec = top.spec,
                        onBack = ::pop,
                        onPickMech = { m -> push(Route.Detail(top.brand, top.spec, m)) },
                    )
                    is Route.Detail -> DetailScreen(
                        theme = theme,
                        brand = top.brand,
                        spec = top.spec,
                        mech = top.mech,
                        onBack = ::pop,
                        onBook = { push(Route.Book(top.brand, top.spec, top.mech)) },
                        onCall = { },
                        onWhatsapp = { },
                        onDirections = { },
                        onReviews = { push(Route.Reviews(top.mech)) },
                    )
                    is Route.Reviews -> ReviewsScreen(
                        theme = theme,
                        mech = top.mech,
                        onBack = ::pop,
                    )
                    is Route.Book -> BookScreen(
                        theme = theme,
                        brand = top.brand,
                        spec = top.spec,
                        mech = top.mech,
                        onBack = ::pop,
                        onConfirm = { replace(Route.Confirm(top.mech)) },
                    )
                    is Route.Confirm -> ConfirmScreen(
                        theme = theme,
                        mech = top.mech,
                        onDone = { reset() },
                    )
                }

                // Floating "Done" button — appears when any input is focused
                AnimatedVisibility(
                    visible = inputFocused,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 48.dp, end = 24.dp),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(theme.accent)
                            .border(1.dp, theme.accent, CircleShape)
                            .clickable {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = strings.done,
                            color = theme.accentText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}
