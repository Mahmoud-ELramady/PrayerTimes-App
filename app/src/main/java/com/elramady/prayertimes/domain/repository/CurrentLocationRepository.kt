package com.elramady.prayertimes.domain.repository

import android.net.Uri
import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.location.AddressLocation
import com.elramady.prayertimes.domain.models.location.CurrentLocation
import com.elramady.prayertimes.domain.models.location.DirectionsLocation
import kotlinx.coroutines.flow.Flow

interface CurrentLocationRepository {
    suspend fun getCurrentLocation(): Flow<Resource<CurrentLocation>>
    suspend fun getAddressFromLocation(location: CurrentLocation): Flow<Resource<AddressLocation>>
    suspend fun openDirectionsOnGoogleMap(directionsLocation: DirectionsLocation): Flow<Resource<Uri>>
}