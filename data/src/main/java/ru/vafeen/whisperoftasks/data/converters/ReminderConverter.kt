package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.whisperoftasks.data.local_database.entity.ReminderEntity
import ru.vafeen.whisperoftasks.domain.converter.BaseConverter
import ru.vafeen.whisperoftasks.domain.models.Reminder

internal class ReminderConverter : BaseConverter<ReminderEntity, Reminder> {

    override fun convertAB(a: ReminderEntity): Reminder {
        return Reminder(
            id = a.id,
            idOfReminder = a.idOfReminder,
            title = a.title,
            text = a.text,
            dt = a.dt,
            repeatDuration = a.repeatDuration,
            dateOfDone = a.dateOfDone,
            isNotificationNeeded = a.isNotificationNeeded
        )
    }

    override fun convertBA(b: Reminder): ReminderEntity {
        return ReminderEntity(
            id = b.id,
            idOfReminder = b.idOfReminder,
            title = b.title,
            text = b.text,
            dt = b.dt,
            repeatDuration = b.repeatDuration,
            dateOfDone = b.dateOfDone,
            isNotificationNeeded = b.isNotificationNeeded
        )
    }
}
