package ru.vafeen.whisperoftasks.data.local_database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.data.local_database.dao.parent.DataAccessObject
import ru.vafeen.whisperoftasks.data.local_database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.whisperoftasks.data.local_database.entity.ReminderEntity

@Dao
internal interface ReminderDao : DataAccessObject<ReminderEntity>, FlowGetAllImplementation<ReminderEntity> {
    @Query("select * from reminderentity")
    override fun getAllAsFlow(): Flow<List<ReminderEntity>>

    @Query("select * from reminderentity where idOfReminder=:idOfReminder limit 1")
    fun getReminderByIdOfReminder(idOfReminder: Int): ReminderEntity?
}