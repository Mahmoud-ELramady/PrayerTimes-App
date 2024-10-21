package com.elramady.prayertimes.domain.use_case.prayer_times

import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.repository.PrayerTimesRepository
import kotlinx.coroutines.flow.Flow
import java.time.Month
import javax.inject.Inject

class GetPrayerTimesFromLocalUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
){
   suspend operator fun invoke(): PrayerTimes?= prayerTimesRepository.getPrayerTimesFromLocal()
}