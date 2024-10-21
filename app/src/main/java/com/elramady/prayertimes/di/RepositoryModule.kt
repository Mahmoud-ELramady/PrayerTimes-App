package com.elramady.prayertimes.di

import android.content.Context
import android.location.Geocoder
import com.elramady.prayertimes.data.local.room.PrayerTimesDatabase
import com.elramady.prayertimes.data.remote.PrayerTimesApi
import com.elramady.prayertimes.data.repositories.current_location.CurrentLocationRepositoryImpl
import com.elramady.prayertimes.data.repositories.prayer_times.PrayerTimesRepositoryImpl
import com.elramady.prayertimes.domain.repository.CurrentLocationRepository
import com.elramady.prayertimes.domain.repository.PrayerTimesRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        prayerTimesApi: PrayerTimesApi,
        prayerTimesDatabase: PrayerTimesDatabase,
    ) : PrayerTimesRepository {
        return PrayerTimesRepositoryImpl(prayerTimesApi,prayerTimesDatabase)
    }

    @Provides
    @Singleton
    fun provideCurrentLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient,
        geocoder: Geocoder
    ) : CurrentLocationRepository {
        return CurrentLocationRepositoryImpl(context,fusedLocationProviderClient,geocoder)
    }


}