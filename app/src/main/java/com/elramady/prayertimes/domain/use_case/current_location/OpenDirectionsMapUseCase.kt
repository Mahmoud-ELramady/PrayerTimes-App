package com.elramady.prayertimes.domain.use_case.current_location

import android.net.Uri
import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.location.DirectionsLocation
import com.elramady.prayertimes.domain.repository.CurrentLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OpenDirectionsMapUseCase @Inject constructor(
    private val repository: CurrentLocationRepository
) {

    suspend  operator fun invoke(directionsLocation: DirectionsLocation): Flow<Resource<Uri>> = repository.openDirectionsOnGoogleMap(directionsLocation)

}