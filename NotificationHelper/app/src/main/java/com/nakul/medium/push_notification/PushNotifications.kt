package com.nakul.medium.push_notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.e("onMessageReceived", "data payload ====${p0.data}")
        Log.e(
            "onMessageReceived",
            "notification payload ====${p0.notification?.body}||${p0.notification?.title}"
        )
        NotificationHelper.displayNotifications(
            applicationContext,
            dataPayload = p0.data,
            notificationPayload = p0.notification
        )
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("onNewToken", "====${p0}")
    }
}


