package com.elramady.prayertimes.data.repositories.current_location

import com.elramady.prayertimes.common.NetworkUtils
import com.elramady.prayertimes.common.Resource

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.elramady.prayertimes.domain.models.location.AddressLocation
import com.elramady.prayertimes.domain.models.location.CurrentLocation
import com.elramady.prayertimes.domain.models.location.DirectionsLocation
import com.elramady.prayertimes.domain.repository.CurrentLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class CurrentLocationRepositoryImpl @Inject constructor(
    private val  context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
): CurrentLocationRepository {


    @Throws(SecurityException::class)
    suspend fun getLastLocation(): CurrentLocation {
        return suspendCancellableCoroutine { continuation ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                Log.e("getCurrentLocation","CurrentLocationRepositoryImpl")
                val task: Task<android.location.Location> = fusedLocationProviderClient.lastLocation
                task.addOnSuccessListener { location ->
                    Log.e("getCurrentLocation", "CurrentLocationRepositoryImpl: $location")

                    if (location != null) {
                        continuation.resume(CurrentLocation(location.latitude, location.longitude))
                    } else {
                        continuation.resumeWithException(NullPointerException("Location is null"))
                    }
                }

                task.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }


            } else {
                continuation.resumeWithException(SecurityException("Location permissions are not granted"))
            }
        }
    }
    private suspend fun getAddressOfLocation(location: CurrentLocation): AddressLocation {
        return suspendCancellableCoroutine { continuation ->
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    address
                    continuation.resume(
                        AddressLocation(
                            country = address.countryName,
                            city = address.locality,
                            street = address.thoroughfare,
                            postalCode = address.postalCode,
                            governorate = address.adminArea
                        )
                    )
                } else {
                    continuation.resumeWithException(Exception("No address found"))
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }


    override suspend fun getCurrentLocation(): Flow<Resource<CurrentLocation>> {
        return NetworkUtils.performOperationWithoutMapper(
            networkCall = { getLastLocation() }
        )
    }

    override suspend fun getAddressFromLocation(location: CurrentLocation): Flow<Resource<AddressLocation>> {
        Log.e("getCurrentLocation","getAddressFromLocation")

        return NetworkUtils.performOperationWithoutMapper (
            networkCall = { getAddressOfLocation(location) }
        )
    }

    private fun openDirections(startLat: Double?, startLng: Double?, endLat: Double?, endLng: Double?): Uri {
        return Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$startLat,$startLng&destination=$endLat,$endLng&travelmode=driving")
    }

    override suspend fun openDirectionsOnGoogleMap(
        directionsLocation: DirectionsLocation
    ): Flow<Resource<Uri>> {
        return NetworkUtils.performOperationWithoutMapper (
            networkCall = {
                openDirections(
                    directionsLocation.startLat,
                    directionsLocation.startLng,
                    directionsLocation.endLat,
                    directionsLocation.endLng
                )
            }
        )
    }




}






