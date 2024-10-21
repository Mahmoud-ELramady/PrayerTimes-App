package com.elramady.prayertimes.domain.models

import com.elramady.prayertimes.data.local.room.DataEntity


data class PrayerTimes(
    val dataPrayerTimes: List<PrayerTimesData?>?
)

data class PrayerTimesData(
    val date: String?,
    val day: String?,
    val timestamp: String?,
    val timings: Timings?
)

data class Timings(
    val asr: String?="",
    val duhr: String?="",
    val fajr: String?="",
    val isha: String?="",
    val maghrib: String?="",
    val sunrise: String?=""
)
