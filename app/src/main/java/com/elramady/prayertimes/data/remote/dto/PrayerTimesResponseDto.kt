package com.elramady.prayertimes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PrayerTimesResponseDto(
    val code: Int?=null,
    val data: List<DataDto>?,
    val status: String?
)
data class DataDto(
    val date: DateDto?,
    val meta: MetaDto?,
    val timings: TimingsDto?
)

data class DateDto(
    val gregorian: GregorianDto?,
    val hijri: HijriDto?,
    val readable: String?,
    val timestamp: String?
)

data class MetaDto(
    val latitude: Double?,
    val latitudeAdjustmentMethod: String?,
    val longitude: Double?,
    val method: MethodDto?,
    val midnightMode: String?,
    val offset: OffsetDto?,
    val school: String?,
    val timezone: String?
)

data class TimingsDto(
    @SerializedName("Asr")
    val asr: String?,

    @SerializedName("Dhuhr")
    val duhr: String?,

    @SerializedName("Fajr")
    val fajr: String?,

    @SerializedName("Firstthird")
    val firstthird: String?,

    @SerializedName("Imsak")
    val imsak: String?,

    @SerializedName("Isha")
    val isha: String?,

    @SerializedName("Lastthird")
    val lastthird: String?,

    @SerializedName("Maghrib")
    val maghrib: String?,

    @SerializedName("Midnight")
    val midnight: String?,

    @SerializedName("Sunrise")
    val sunrise: String?,

    @SerializedName("Sunset")
    val sunset: String?
)

data class GregorianDto(
    val date: String?,
    val day: String?,
    val designation: DesignationDto,
    val format: String?,
    val month: MonthDto?,
    val weekday: WeekdayDto?,
    val year: String?
)
data class HijriDto(
    val date: String?,
    val day: String?,
    val designation: DesignationDto?,
    val format: String?,
    val holidays: List<Any>? = emptyList(),
    val month: MonthDto?,
    val weekday: WeekdayDto?,
    val year: String?
)

data class MethodDto(
    val id: Int?,
    val location: LocationDto?,
    val name: String?,
    val params: ParamsDto?
)

data class OffsetDto(
    val Asr: Int?,
    val Dhuhr: Int?,
    val Fajr: Int?,
    val Imsak: Int?,
    val Isha: Int?,
    val Maghrib: Int?,
    val Midnight: Int?,
    val Sunrise: Int?,
    val Sunset: Int?
)

data class DesignationDto(
    val abbreviated: String?,
    val expanded: String?
)
data class MonthDto(
    val ar: String?,
    val en: String?,
    val number: Int?
)
data class WeekdayDto(
    val ar: String?,
    val en: String?
)
data class LocationDto(
    val latitude: Double?,
    val longitude: Double?
)
data class ParamsDto(

    @SerializedName("Fajr")
    val fajr: Int?,

    @SerializedName("Isha")
    val isha: Int?,)