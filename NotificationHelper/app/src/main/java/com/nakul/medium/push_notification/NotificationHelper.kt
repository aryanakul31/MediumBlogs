package com.nakul.medium.push_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.nakul.medium.MainActivity
import com.nakul.medium.R

object NotificationHelper {

    fun getFcmToken(submitToken: (token: String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task: Task<String> ->
            when {
                task.isSuccessful -> submitToken(task.result)
            }
        }
    }

    private fun getBundle(remoteMessage: MutableMap<String, String>?): Bundle? {
        val bundle = Bundle()
        remoteMessage?.forEach {
            bundle.putString(it.key, it.value)
        }
        return if (bundle.isEmpty) null else bundle
    }

    fun displayNotifications(
        context: Context,
        dataPayload: MutableMap<String, String> = HashMap(),
        notificationPayload: RemoteMessage.Notification?,
    ) {

        val bundle = getBundle(dataPayload) ?: return
        if (notificationPayload?.title?.isNotBlank() == true) bundle.putString(
            "title",
            notificationPayload.title
        )
        if (notificationPayload?.body?.isNotBlank() == true) bundle.putString(
            "body",
            notificationPayload.body
        )


        val title = bundle.getString("title")
        val description = bundle.getString("body")

        val soundNumber =
            if (bundle.containsKey("sound")) bundle.getString("sound")?.toInt() else null

        val pendingIntent = PendingIntent.getActivity(
            context, 101, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )

        val notificationType = NotificationType.getNotificationType(soundNumber)

        val sound =
            if (notificationType.soundNumber == null) RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            else Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/raw/${notificationType.soundEndPoint}")

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setContentTitle(title).setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId(context.getString(notificationType.channelName)).setSound(sound)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent)


        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mChannel = NotificationChannel(
            context.getString(notificationType.channelName),
            context.getString(notificationType.channelName),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            setShowBadge(true)
            setAllowBubbles(true)
            importance = NotificationManager.IMPORTANCE_HIGH
            enableVibration(true)
            setDescription(description)
            setSound(sound, Notification.AUDIO_ATTRIBUTES_DEFAULT)
        }

        notificationManager.createNotificationChannel(mChannel)
        notificationManager.notify(notificationType.notificationId, builder.build())
    }
}