package ru.vafeen.whisperoftasks.domain.noui.notification

object NotificationChannel {
    object Task : ChannelInfo {
        override val NOTIFICATION_CHANNEL_ID = "Task channel"
        override val NOTIFICATION_CHANNEL_NAME = "Tasks"
        override val REQUEST_CODE = 200
    }

    object ReminderRecovery : ChannelInfo {
        override val NOTIFICATION_CHANNEL_ID: String = "Reminder recovery"
        override val NOTIFICATION_CHANNEL_NAME: String = "Reminder recovery"
        override val REQUEST_CODE: Int = 202
    }
}