package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.project.data.Mechanic
import org.example.project.data.Shop
import org.example.project.data.auth.UserDto
import org.example.project.ui.components.BottomNavBar
import org.example.project.ui.components.MainTab
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun MainShell(
    theme: CraftsmenColors,
    onPickMech: (Mechanic) -> Unit,
    onBrowseCatalog: () -> Unit,
    onPickShop: (Shop) -> Unit,
    currentUser: UserDto?,
    onLogout: () -> Unit,
    onOpenDashboard: () -> Unit,
    onSignIn: () -> Unit,
) {
    var tab by remember { mutableStateOf(MainTab.Home) }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        Box(modifier = Modifier.weight(1f)) {
            when (tab) {
                MainTab.Home -> HomeScreen(
                    theme = theme,
                    onBrowseCatalog = onBrowseCatalog,
                    onPickMech = onPickMech,
                )
                MainTab.Shops -> ShopsScreen(
                    theme = theme,
                    onPickShop = onPickShop,
                )
                MainTab.Garage -> GarageScreen(
                    theme = theme,
                    user = currentUser,
                    onSignIn = onSignIn,
                )
                MainTab.Profile -> SettingsScreen(
                    theme = theme,
                    user = currentUser,
                    onLogout = onLogout,
                    onOpenDashboard = onOpenDashboard,
                    onSignIn = onSignIn,
                )
            }
        }
        BottomNavBar(
            theme = theme,
            selected = tab,
            onSelect = { tab = it },
        )
    }
}
