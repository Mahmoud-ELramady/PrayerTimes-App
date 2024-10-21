package com.elramady.prayertimes.domain.repository

import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.QiblaDirectionResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerTimesRepository {
    suspend fun getPrayerTimesFromRemote(
        year:String,
        month:String,
        prayerTimesDataRequest: PrayerTimesDataRequest
    ): Flow<Resource<PrayerTimes>>

    suspend fun getPrayerTimesFromLocal(
    ): PrayerTimes?


    suspend fun getQiblaDirection(
        lat: Double,
         lng: Double
    ):Flow<Resource<QiblaDirectionResponse>>
}
