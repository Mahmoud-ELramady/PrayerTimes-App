package com.elramady.prayertimes.presentation.prayers_times

import android.net.Uri
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.QiblaDirectionResponse
import com.elramady.prayertimes.domain.models.location.AddressLocation
import com.elramady.prayertimes.domain.models.location.CurrentLocation


data class PrayerTimesScreenState(
    val isLoading:Boolean=false,
    val error:String="",
    val userLocation: CurrentLocation?=null,
    val address: AddressLocation?=AddressLocation(),
    val prayerTimesList:PrayerTimes?=null,
    val prayerTimeRequest: PrayerTimesDataRequest?=null,
    val qiblaDirection:QiblaDirectionResponse?=null,
    val currentDate:String="",
    val year:String="",
    val month:String="",
    val day:String="",
    val nextPrayerName:String="",
    val nextPrayerTimeLeft:String="",

)
