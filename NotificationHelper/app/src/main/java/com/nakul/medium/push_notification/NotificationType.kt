package com.nakul.medium.push_notification

import com.nakul.medium.R

enum class NotificationType(
    val channelName: Int,
    val soundNumber: Int?,
    val soundEndPoint: Int?,
    val notificationId: Int
) {
    SOUND_1(
        R.string.notification_title_1,
        1,
        R.raw.rocket,
        1_001
    ),
    SOUND_2(
        R.string.notification_title_2,
        2,
        R.raw.small_sms_tone,
        1_002
    ),
    SOUND_3(
        R.string.notification_title_3,
        3,
        R.raw.ting,
        1_003
    ),
    SOUND_4(
        R.string.notification_title_4,
        4,
        R.raw.water_droplet,
        1_004
    ),
    DEFAULT(R.string.notification_title_default, null, null, 1_005);

    companion object {
        fun getNotificationType(soundNumber: Int?): NotificationType {
            NotificationType.values().forEach {
                if (it.soundNumber == soundNumber) return it
            }
            return DEFAULT
        }
    }
}