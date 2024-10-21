package com.elramady.prayertimes.data.local.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.elramady.prayertimes.data.local.room.converter.DataEntityConverter

@Entity
data class PrayerTimesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val dataEntity: List<DataEntity>
)


data class DataEntity(
    val dateEntity: DateEntity?,
    val timingsEntity: TimingsEntity?
)


data class DateEntity(
    val readableEntity: String?,
    val timestampEntity: String?,
    val dayEntity:String?
)



data class TimingsEntity(
    val asr: String?,
    val duhr: String?,
    val fajr: String?,
    val isha: String?,
    val maghrib: String?,
    val sunrise: String?,
)




