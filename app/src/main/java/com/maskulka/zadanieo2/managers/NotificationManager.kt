package com.maskulka.zadanieo2.managers

import android.app.Application
import io.karn.notify.Notify

class NotificationManager(
    private val application: Application
) {
    fun showNotification(notificationTitle: String, notificationDesc: String) {
        Notify
            .with(application)
            .content {
                title = notificationTitle
                text = notificationDesc
            }
            .show()
    }
}