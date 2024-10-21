package com.elramady.prayertimes.domain.use_case.prayer_times

import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.repository.PrayerTimesRepository
import kotlinx.coroutines.flow.Flow
import java.time.Month
import javax.inject.Inject

class GetPrayerTimesFromRemoteUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
){
   suspend operator fun invoke(
       year: String,
       month: String,
       prayerTimesDataRequest: PrayerTimesDataRequest,
   ): Flow<Resource<PrayerTimes>> = prayerTimesRepository.getPrayerTimesFromRemote(
       year = year,
       month = month,
       prayerTimesDataRequest = prayerTimesDataRequest
   )
}