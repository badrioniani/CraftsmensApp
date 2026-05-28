package org.example.project.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class MapPin(
    val id: String,
    val lat: Double,
    val lng: Double,
    val title: String,
    val subtitle: String? = null,
)

data class MapCamera(
    val lat: Double,
    val lng: Double,
    val zoom: Float = 12f,
)

/**
 * Cross-platform map. Android renders Google Maps via maps-compose;
 * iOS renders Apple Maps (MKMapView) via UIKit interop.
 *
 * - `selectedPinId` highlights the matching pin.
 * - `showUserLocation` enables the blue "you are here" dot when permission is granted.
 */
@Composable
expect fun MapView(
    modifier: Modifier,
    pins: List<MapPin>,
    camera: MapCamera,
    selectedPinId: String? = null,
    showUserLocation: Boolean = false,
    onPinClick: (String) -> Unit = {},
    // When non-null, tapping the map reports the tapped (lat, lng) — used for
    // location picking. Left null on display-only maps so taps stay inert.
    onMapTap: ((Double, Double) -> Unit)? = null,
)

/** Best-effort current location lookup. Returns null if unavailable / denied. */
@Composable
expect fun rememberCurrentLocation(): MapCamera?
