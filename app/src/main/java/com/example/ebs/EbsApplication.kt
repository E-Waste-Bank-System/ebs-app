package com.example.ebs

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ebs.data.repositories.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp

private const val APP_MEMORY = "appMemory"
private val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = APP_MEMORY)

@HiltAndroidApp
class EbsApplication: Application() {
    internal lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        //container = AppDataContainer(this)
        super.onCreate()
        userPreferencesRepository =
            UserPreferencesRepository(dataStore)

        val notificationChannel = NotificationChannel(
            "ebs_notification",
            "E-Hub Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }
}