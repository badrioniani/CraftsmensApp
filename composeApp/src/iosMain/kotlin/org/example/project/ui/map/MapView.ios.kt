@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlinx.cinterop.BetaInteropApi::class)

package org.example.project.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.Foundation.NSSelectorFromString
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKPointAnnotation
import platform.UIKit.UITapGestureRecognizer
import platform.darwin.NSObject

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
    val mapView = remember { MKMapView() }

    // Tap-to-place: attach a tap recognizer once and keep its callback fresh.
    // cancelsTouchesInView=false so it never swallows annotation-selection taps.
    val tapHandler = remember { MapTapHandler(mapView) }
    tapHandler.onTap = onMapTap
    LaunchedEffect(Unit) {
        val recognizer = UITapGestureRecognizer(target = tapHandler, action = NSSelectorFromString("handleTap:"))
        recognizer.setCancelsTouchesInView(false)
        mapView.addGestureRecognizer(recognizer)
    }

    // Pin selection: a delegate forwards annotation taps back to onPinClick.
    val selectionDelegate = remember { MapSelectionDelegate() }
    selectionDelegate.onSelect = onPinClick
    LaunchedEffect(Unit) { mapView.setDelegate(selectionDelegate) }

    LaunchedEffect(camera) {
        val region = MKCoordinateRegionMakeWithDistance(
            centerCoordinate = CLLocationCoordinate2DMake(camera.lat, camera.lng),
            latitudinalMeters = 6000.0,
            longitudinalMeters = 6000.0,
        )
        mapView.setRegion(region, animated = true)
    }

    LaunchedEffect(pins) {
        // Wipe and re-add — keeps the list in sync with current state.
        mapView.removeAnnotations(mapView.annotations)
        pins.forEach { pin ->
            val annotation = IdAnnotation(pin.id)
            annotation.setCoordinate(CLLocationCoordinate2DMake(pin.lat, pin.lng))
            annotation.setTitle(pin.title)
            annotation.setSubtitle(pin.subtitle ?: "")
            mapView.addAnnotation(annotation)
        }
    }

    LaunchedEffect(showUserLocation) {
        mapView.setShowsUserLocation(showUserLocation)
    }

    UIKitView(
        factory = { mapView },
        modifier = modifier,
    )
}

/** Point annotation that remembers which [MapPin] it represents. */
private class IdAnnotation(val pinId: String) : MKPointAnnotation()

/** Forwards MapKit annotation selection back to a Kotlin callback. */
private class MapSelectionDelegate : NSObject(), MKMapViewDelegateProtocol {
    var onSelect: ((String) -> Unit)? = null

    override fun mapView(mapView: MKMapView, didSelectAnnotationView: MKAnnotationView) {
        (didSelectAnnotationView.annotation as? IdAnnotation)?.let { onSelect?.invoke(it.pinId) }
    }
}

/** Bridges UIKit tap gestures on the map to a Kotlin callback. */
private class MapTapHandler(private val mapView: MKMapView) : NSObject() {
    var onTap: ((Double, Double) -> Unit)? = null

    @ObjCAction
    fun handleTap(recognizer: UITapGestureRecognizer) {
        val callback = onTap ?: return
        val point = recognizer.locationInView(mapView)
        val coordinate = mapView.convertPoint(point, toCoordinateFromView = mapView)
        coordinate.useContents { callback(latitude, longitude) }
    }
}

@Composable
actual fun rememberCurrentLocation(): MapCamera? {
    val manager = remember { CLLocationManager() }
    var result by remember { mutableStateOf<MapCamera?>(null) }
    LaunchedEffect(Unit) {
        val status = CLLocationManager.authorizationStatus()
        val granted = status == kCLAuthorizationStatusAuthorizedAlways ||
            status == kCLAuthorizationStatusAuthorizedWhenInUse
        if (!granted) {
            manager.requestWhenInUseAuthorization()
            return@LaunchedEffect
        }
        manager.location?.let { loc ->
            loc.coordinate.useContents {
                result = MapCamera(latitude, longitude, zoom = 13f)
            }
        }
    }
    return result
}
