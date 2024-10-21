package com.elramady.prayertimes.di

import android.content.Context
import androidx.room.Room
import com.elramady.prayertimes.data.local.room.PrayerTimesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PrayerTimesDatabase = Room.databaseBuilder(
        context,
        PrayerTimesDatabase::class.java,
        "prayers_times.db"
    ).build()

}