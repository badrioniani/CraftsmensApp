package org.example.project.ui.util

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

private const val EARTH_RADIUS_KM = 6371.0088

/**
 * Great-circle distance in kilometres between two `(lat, lng)` pairs.
 * Coordinates are in degrees; results are accurate to within ~0.5% on Earth.
 */
fun haversineKm(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    val dLat = (lat2 - lat1).toRadians()
    val dLng = (lng2 - lng1).toRadians()
    val a = sin(dLat / 2).let { it * it } +
        cos(lat1.toRadians()) * cos(lat2.toRadians()) *
        sin(dLng / 2).let { it * it }
    return 2 * EARTH_RADIUS_KM * asin(min(1.0, sqrt(a)))
}

private fun Double.toRadians(): Double = this * PI / 180.0

/**
 * Compact distance label: "850 [mSuffix]" / "2.3 [kmSuffix]" / "12 [kmSuffix]".
 * Returns null when [km] is unknown (infinite / NaN) so callers can hide the
 * UI affordance entirely instead of showing "∞".
 */
fun formatDistance(km: Double, kmSuffix: String, mSuffix: String): String? {
    if (km.isNaN() || km.isInfinite() || km < 0.0) return null
    if (km < 1.0) {
        val meters = (km * 1000).toInt().coerceAtLeast(0)
        return "$meters $mSuffix"
    }
    if (km < 10.0) {
        val tenths = (km * 10).toLong()
        val whole = tenths / 10
        val dec = tenths % 10
        return "$whole.$dec $kmSuffix"
    }
    return "${km.toInt()} $kmSuffix"
}
