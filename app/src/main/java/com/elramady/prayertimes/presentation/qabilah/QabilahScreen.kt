package com.elramady.prayertimes.presentation.qabilah

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elramady.prayertimes.MainActivity
import com.elramady.prayertimes.R
import com.elramady.prayertimes.presentation.prayers_times.PrayerTimesViewModel
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2



@Composable
fun QabilahScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel: PrayerTimesViewModel = viewModel(LocalContext.current as MainActivity)
    val prayerScreenState = viewModel.prayerTimesScreenState.value

    var myLatLng: LatLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var kabaaLatLng: LatLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var markerKabaaBitmap by remember { mutableStateOf<Bitmap?>(null) }

    prayerScreenState.userLocation?.let {
        myLatLng = LatLng(it.latitude, it.longitude)
    }

    LaunchedEffect(key1 = prayerScreenState.userLocation) {
        viewModel.getDirectionQibla()
    }
    val markerStateKabba = rememberMarkerState(position = kabaaLatLng)

    LaunchedEffect(key1 = prayerScreenState.qiblaDirection) {
        prayerScreenState.qiblaDirection?.data?.let { qiblaData ->
            val qiblaDirection = qiblaData.direction // Qibla direction in degrees
            kabaaLatLng = calculateQiblaPosition(myLatLng, qiblaDirection)
            Log.e("kabaaLatLng", kabaaLatLng.toString())
        }
        markerStateKabba.position=kabaaLatLng
        markerKabaaBitmap = convertPngToBitmap(context, R.drawable.kaaba_icon)
        Log.e("markerKabaaBitmap", markerKabaaBitmap.toString())
    }

    // Initialize Gyroscope Sensor Manager
    val gyroscopeSensorManager = remember { GyroscopeSensorManager(context) }
    val gyroscopeRotation by gyroscopeSensorManager.rotation.collectAsState()


    Log.e("gyroscopeRotation", gyroscopeRotation.toString())
    val cameraZoom = 15f
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLatLng, cameraZoom)
    }

    val markerStateLocation = rememberMarkerState(position = myLatLng)

    var mapType by remember { mutableStateOf(MapType.NORMAL) }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
            properties = MapProperties(
                maxZoomPreference = 25f,
                mapType = mapType,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
            )
        ) {
            Marker(
                state = markerStateLocation,
                title = "Your Location"
            )

            if (markerKabaaBitmap != null) {
                val markerIcon = BitmapDescriptorFactory.fromBitmap(markerKabaaBitmap!!)

                MarkerInfoWindow(
                    icon = markerIcon,
                    state = markerStateKabba ,
                    onClick = { false },
                    onInfoWindowClick = {
                        Log.e("onClickInfoMap", "onClickInfoMap")
                    }
                )

                // Draw the polyline from user location to Kaaba
                Polyline(
                    points = listOf(myLatLng, kabaaLatLng),
                    color = Color.Red,
                    width = 5f
                )
            }

            // Draw the dynamic Qibla arrow with rotation based on gyroscope
            prayerScreenState.qiblaDirection?.data?.direction?.let { qiblaDirection ->
                // Custom Arrow Bitmap
                val arrowBitmap = BitmapFactory.decodeResource(
                    context.resources, R.drawable.right_arrow
                )
                val markerIcon = BitmapDescriptorFactory.fromBitmap(arrowBitmap)

                // Calculate the adjusted rotation of the arrow
                val adjustedRotation = (qiblaDirection - gyroscopeRotation) % 360

                // Check if the arrow is aligned with Qibla direction (within a 5-degree threshold)
                if (Math.abs(adjustedRotation) <= 2) {
                  //  Toast.makeText(context, "Aligned with Qibla", Toast.LENGTH_SHORT).show()
                }

                // Marker for Qibla arrow at the user's location with dynamic rotation
                Marker(
                    state = rememberMarkerState(position = myLatLng),
                    icon = markerIcon,
                    rotation = adjustedRotation.toFloat(), // Rotate based on gyroscope and Qibla
                    anchor = Offset(0.1f, 0.5f)  // Center the rotation on the marker
                )
            }
        }
    }
}







fun convertPngToBitmap(context: Context, pngResourceId: Int): Bitmap? {
    // Get the InputStream from the resource (PNG file)
    val inputStream = context.resources.openRawResource(pngResourceId)

    // Use BitmapFactory to decode the InputStream and convert it to a Bitmap
    return BitmapFactory.decodeStream(inputStream)
}




















// Helper function to calculate Qibla direction
fun calculateQiblaDirection(currentLocation: LatLng, qiblaLocation: LatLng): Float {
    val latDiff = qiblaLocation.latitude - currentLocation.latitude
    val lonDiff = qiblaLocation.longitude - currentLocation.longitude

    return Math.toDegrees(Math.atan2(lonDiff, latDiff)).toFloat()
}





// Function to calculate the position of Qibla based on your current location and the direction
private fun calculateQiblaPosition(currentLocation: LatLng, qiblaDirection: Double): LatLng {
    // Earth radius in kilometers
    val earthRadius = 6371.0

    // Convert the latitude and longitude from degrees to radians
    val lat = Math.toRadians(currentLocation.latitude)
    val lng = Math.toRadians(currentLocation.longitude)
    val bearing = Math.toRadians(qiblaDirection)

    // Calculate the Qibla latitude and longitude
    val qiblaLat = Math.asin(Math.sin(lat) * Math.cos(1.0 / earthRadius) +
            Math.cos(lat) * Math.sin(1.0 / earthRadius) * Math.cos(bearing))

    val qiblaLng = lng + Math.atan2(
        Math.sin(bearing) * Math.sin(1.0 / earthRadius) * Math.cos(lat),
        Math.cos(1.0 / earthRadius) - Math.sin(lat) * Math.sin(qiblaLat)
    )

    return LatLng(Math.toDegrees(qiblaLat), Math.toDegrees(qiblaLng))
}









