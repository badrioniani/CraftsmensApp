package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import org.example.project.ui.theme.ACCENT_OPTIONS
import org.example.project.ui.theme.LocalCraftsmenColors
import org.example.project.ui.theme.buildTheme

@Composable
@Preview
fun App() {
    val accent = remember { ACCENT_OPTIONS.first().color }
    val dark by remember { mutableStateOf(true) }
    val layout by remember { mutableStateOf(ListLayout.List) }
    val theme = remember(dark, accent) { buildTheme(dark, accent) }

    val stack = remember { mutableStateListOf<Route>(Route.Home) }
    fun push(r: Route) { stack.add(r) }
    fun pop() { if (stack.size > 1) stack.removeAt(stack.lastIndex) }
    fun reset() { stack.clear(); stack.add(Route.Home) }
    fun replace(r: Route) {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)
        stack.add(r)
    }

    MaterialTheme {
        CompositionLocalProvider(LocalCraftsmenColors provides theme) {
            Box(modifier = Modifier.fillMaxSize().background(theme.bg)) {
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
            }
        }
    }
}
