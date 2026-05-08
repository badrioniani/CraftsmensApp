package org.example.project.nav

import org.example.project.data.AutoPart
import org.example.project.data.CarBrand
import org.example.project.data.Mechanic
import org.example.project.data.Specialty

enum class UserRole { User, Mechanic }

sealed class Route {
    data object Splash : Route()
    data object Login : Route()
    data object ForgotPassword : Route()
    data class ResetPassword(val email: String, val prefilledCode: String? = null) : Route()
    data object RegisterPicker : Route()
    data class Register(val role: UserRole) : Route()
    data object Home : Route()
    data class SpecPicker(val brand: CarBrand) : Route()
    data class MechList(val brand: CarBrand, val spec: Specialty) : Route()
    data class MapView(val brand: CarBrand, val spec: Specialty) : Route()
    data class Detail(val brand: CarBrand, val spec: Specialty, val mech: Mechanic) : Route()
    data class Reviews(val mech: Mechanic) : Route()
    data class Book(val brand: CarBrand, val spec: Specialty, val mech: Mechanic) : Route()
    data class Confirm(val mech: Mechanic) : Route()
    data class PartDetail(val part: AutoPart) : Route()
}
