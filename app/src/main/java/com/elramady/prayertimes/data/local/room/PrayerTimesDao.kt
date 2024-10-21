package com.elramady.prayertimes.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PrayerTimesDao {

    @Upsert
    suspend fun insertPrayerTimes(prayerTimesEntity: PrayerTimesEntity)

    @Query(
        "SELECT * FROM PrayerTimesEntity"
    )
    suspend fun getPrayerTimes(): PrayerTimesEntity?

    @Query("DELETE FROM PrayerTimesEntity")
    suspend fun clearPrayerTimes()
}