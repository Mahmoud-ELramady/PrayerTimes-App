package com.elramady.prayertimes.data.remote

import com.elramady.prayertimes.data.remote.dto.PrayerTimesResponseDto
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.QiblaDirectionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PrayerTimesApi {

    @GET("calendar/{year}/{month}")
    suspend fun getPrayerTimes(
        @Path("year") year:String,
        @Path("month") month: String,
        @QueryMap(encoded = true) prayerTimesDataQueryMap: Map<String,String>? =null,
        ): PrayerTimesResponseDto

    @GET("qibla/{latitude}/{longitude}")
    suspend fun getQiblaDirection(
        @Path("latitude") lat: Double=21.422487,
        @Path("longitude") lng: Double=39.826206
    ):QiblaDirectionResponse
}

