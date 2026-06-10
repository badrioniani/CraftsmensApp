package org.example.project.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
actual fun MapView(
    modifier: Modifier,
    pins: List<MapPin>,
    camera: MapCamera,
    selectedPinId: String?,
    showUserLocation: Boolean,
    onPinClick: (String) -> Unit,
    onMapTap: ((Double, Double) -> Unit)?,
) {
    val context = LocalContext.current
    val hasFineLocation = remember(showUserLocation) {
        showUserLocation && ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(camera.lat, camera.lng), camera.zoom)
    }
    LaunchedEffect(camera) {
        cameraPositionState.position =
            CameraPosition.fromLatLngZoom(LatLng(camera.lat, camera.lng), camera.zoom)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = hasFineLocation),
        onMapClick = { latLng -> onMapTap?.invoke(latLng.latitude, latLng.longitude) },
    ) {
        pins.forEach { pin ->
            val hue = if (pin.id == selectedPinId) BitmapDescriptorFactory.HUE_AZURE
            else BitmapDescriptorFactory.HUE_ORANGE
            Marker(
                state = MarkerState(position = LatLng(pin.lat, pin.lng)),
                title = pin.title,
                snippet = pin.subtitle,
                icon = BitmapDescriptorFactory.defaultMarker(hue),
                onClick = {
                    onPinClick(pin.id)
                    false
                },
            )
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
actual fun rememberCurrentLocation(): MapCamera? {
    val context = LocalContext.current
    var result by remember { mutableStateOf<MapCamera?>(null) }
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) return@LaunchedEffect
        runCatching {
            val client = LocationServices.getFusedLocationProviderClient(context)
            // lastLocation is cached and can be null on a fresh install / after a
            // device reboot before any other app has requested a fix. Fall back
            // to getCurrentLocation() which actively triggers GPS so the first
            // visit to the list still produces a sortable distance.
            client.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    result = MapCamera(loc.latitude, loc.longitude, zoom = 13f)
                } else {
                    client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
                        .addOnSuccessListener { fresh ->
                            if (fresh != null) {
                                result = MapCamera(fresh.latitude, fresh.longitude, zoom = 13f)
                            }
                        }
                }
            }
        }
    }
    return result
}
