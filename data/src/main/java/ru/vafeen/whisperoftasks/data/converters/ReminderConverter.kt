package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.universityschedule.domain.converter.TwoWayBaseConverter
import ru.vafeen.whisperoftasks.data.local_database.entity.ReminderEntity
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

internal class ReminderConverter : TwoWayBaseConverter<ReminderEntity, Reminder> {
    override fun convertAB(a: ReminderEntity): Reminder = Reminder(
        id = a.id,
        idOfReminder = a.idOfReminder,
        title = a.title,
        text = a.text,
        dt = a.dt,
        repeatDuration = a.repeatDuration,
        dateOfDone = a.dateOfDone,
        isNotificationNeeded = a.isNotificationNeeded
    )

    override fun convertBA(b: Reminder): ReminderEntity = ReminderEntity(
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
