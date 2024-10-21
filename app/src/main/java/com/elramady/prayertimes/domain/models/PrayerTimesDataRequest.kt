package com.elramady.prayertimes.domain.models

data class PrayerTimesDataRequest(
    val latitude:Double?=null,
    val longitude:Double?=null,
    val method:Int?=null
)
