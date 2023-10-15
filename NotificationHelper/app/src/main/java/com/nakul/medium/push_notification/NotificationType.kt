package com.nakul.medium.push_notification

import com.nakul.medium.R

enum class NotificationType(
    val channelName: Int,
    val soundNumber: Int?,
    val soundEndPoint: Int?
) {
    SOUND_1(
        R.string.notification_title_1,
        1,
        R.raw.rocket
    ),
    SOUND_2(
        R.string.notification_title_2,
        2,
        R.raw.small_sms_tone
    ),
    SOUND_3(
        R.string.notification_title_3,
        3,
        R.raw.ting
    ),
    SOUND_4(
        R.string.notification_title_4,
        4,
        R.raw.water_droplet
    ),
    DEFAULT(R.string.notification_title_default, null, null);

    companion object {
        fun getNotificationType(soundNumber: Int?): NotificationType {
            NotificationType.values().forEach {
                if (it.soundNumber == soundNumber) return it
            }
            return DEFAULT
        }
    }
}