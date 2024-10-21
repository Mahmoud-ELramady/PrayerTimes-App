package com.elramady.prayertimes.domain.use_case.qibla_direction

import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.QiblaDirectionResponse
import com.elramady.prayertimes.domain.repository.PrayerTimesRepository
import kotlinx.coroutines.flow.Flow
import java.time.Month
import javax.inject.Inject
import kotlin.math.ln

class GetQiblaDirectionUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
){
   suspend operator fun invoke(
       lat: Double,
       lng: Double
   ): Flow<Resource<QiblaDirectionResponse>> =
       prayerTimesRepository.getQiblaDirection(
           lat = lat,
           lng = lng,
       )
}