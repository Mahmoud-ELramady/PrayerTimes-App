package com.elramady.prayertimes.domain.use_case.current_location

import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.location.AddressLocation
import com.elramady.prayertimes.domain.models.location.CurrentLocation
import com.elramady.prayertimes.domain.repository.CurrentLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressLocationUseCase @Inject constructor(
    private val repository: CurrentLocationRepository
) {


    suspend  operator fun invoke(location: CurrentLocation): Flow<Resource<AddressLocation>> = repository.getAddressFromLocation(location)



}