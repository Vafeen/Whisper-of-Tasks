package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object AppDatabaseMigrationManager {

    private var _migrations = listOf<Migration>(
        object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Reminder RENAME TO ReminderEntity")
            }
        }
    )
    var migrations = _migrations.toTypedArray()

}