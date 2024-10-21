package com.elramady.prayertimes.data.mappers.prayer_times

import com.elramady.prayertimes.data.local.room.DataEntity
import com.elramady.prayertimes.data.local.room.DateEntity
import com.elramady.prayertimes.data.local.room.PrayerTimesEntity
import com.elramady.prayertimes.data.local.room.TimingsEntity
import com.elramady.prayertimes.data.remote.dto.DataDto
import com.elramady.prayertimes.data.remote.dto.DateDto
import com.elramady.prayertimes.data.remote.dto.PrayerTimesResponseDto
import com.elramady.prayertimes.data.remote.dto.TimingsDto
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesData
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.Timings

// From Dto to Entity
fun PrayerTimesResponseDto.toPrayerTimesEntity(): PrayerTimesEntity {
    return PrayerTimesEntity(
        dataEntity = data?.map { it.toDataEntity() } ?: emptyList<DataEntity>()
    )
}

fun DataDto.toDataEntity(): DataEntity {
    return DataEntity(
        dateEntity = date?.toDateEntity(),
        timingsEntity = timings?.toTimingsEntity()
    )

}

fun DateDto.toDateEntity(): DateEntity {
    return DateEntity(
        readableEntity = readable,
        timestampEntity = timestamp,
        dayEntity = gregorian?.day
    )

}



fun TimingsDto.toTimingsEntity(): TimingsEntity {
    return TimingsEntity(
        duhr = duhr,
        asr = asr,
        isha = isha,
        maghrib = maghrib,
        sunrise = sunrise,
        fajr = fajr
    )
}


//From Entity to Domain Model
fun PrayerTimesEntity.toPrayerTimes(): PrayerTimes {
    return PrayerTimes(
      dataPrayerTimes = dataEntity?.map { it?.toPrayerTimesData() }
    )
}


fun DataEntity.toPrayerTimesData(): PrayerTimesData {
    return PrayerTimesData(
       timings =timingsEntity?.toTimings() ,
        date = dateEntity?.readableEntity ,
        day = dateEntity?.dayEntity,
        timestamp =dateEntity?.timestampEntity ,
    )

}
fun TimingsEntity.toTimings(): Timings {
    return Timings(
        duhr = duhr,
        asr = asr,
        isha = isha,
        maghrib = maghrib,
        sunrise = sunrise,
        fajr = fajr
    )
}

//From DTO to Domain Model
fun PrayerTimesResponseDto.toPrayerTimes(): PrayerTimes {
    return PrayerTimes(
        dataPrayerTimes =data?.map { it?.toPrayerTimesData() }
    )
}

fun DataDto.toPrayerTimesData(): PrayerTimesData {
    return PrayerTimesData(
        timings =timings?.toTimings() ,
        date = date?.readable ,
        day = date?.gregorian?.day,
        timestamp = date?.timestamp ,
    )

}

fun TimingsDto.toTimings(): Timings {
    return Timings(
        duhr = duhr,
        asr = asr,
        isha = isha,
        maghrib = maghrib,
        sunrise = sunrise,
        fajr = fajr
    )
}

fun PrayerTimesDataRequest.toQueryMap(): Map<String, String> {
    return mapOf(
        "latitude" to latitude.toString(),
        "longitude" to longitude.toString(),
        "method" to method.toString()
    )
}









