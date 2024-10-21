package com.elramady.prayertimes.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elramady.prayertimes.data.local.room.converter.DataEntityConverter


@Database(
    entities = [PrayerTimesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataEntityConverter::class)
abstract class PrayerTimesDatabase: RoomDatabase() {
    abstract val prayerTimesDao:PrayerTimesDao
}