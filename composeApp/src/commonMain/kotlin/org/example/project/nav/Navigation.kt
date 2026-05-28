package org.example.project.nav

import org.example.project.data.Mechanic
import org.example.project.data.Shop

enum class UserRole { User, Mechanic }

sealed class Route {
    data object Splash : Route()
    data object Login : Route()
    data object ForgotPassword : Route()
    data class ResetPassword(val email: String, val prefilledCode: String? = null) : Route()
    data object RegisterPicker : Route()
    data class Register(val role: UserRole) : Route()

    // Main shell — bottom tabs render inside
    data object Home : Route()

    // Drill-in screens
    data class MechanicsList(val initialQuery: String = "") : Route()
    data class MechanicDetail(val mech: Mechanic) : Route()
    data class Reviews(val mech: Mechanic) : Route()
    data object MapView : Route()
    data class ShopDetail(val shop: Shop) : Route()
    data object MechanicDashboard : Route()
}
