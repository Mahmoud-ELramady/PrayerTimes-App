package com.elramady.prayertimes

import android.app.Application
import androidx.core.os.BuildCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PrayerApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        BuildConfig.MAPS_API_KEY

    }
}