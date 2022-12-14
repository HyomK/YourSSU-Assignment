package com.assignment2

import android.app.Application
import com.assignment2.util.PreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Assignment2 :Application() {

    companion object{
        lateinit var preferences: PreferenceManager
    }

    override fun onCreate() {
        preferences = PreferenceManager(applicationContext)
        super.onCreate()
    }
}