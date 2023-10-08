package com.nakul.medium

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nakul.medium.push_notification.NotificationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationHelper.getFcmToken {
            Log.e("Fcm TOKEN", it)
        }

        val remoteMessages = HashMap<String, String>()
        remoteMessages["title"] = "Notification Title"
        remoteMessages["message"] = "Notification Message"
        remoteMessages["sound"] = "4"
        NotificationHelper.displayNotifications(this, remoteMessages, null)
    }
}