package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object AppDatabaseMigrationManager {
    private fun createMigration(
        startVersion: Int,
        endVersion: Int, migration: (SupportSQLiteDatabase) -> Unit
    ): Migration =
        object : Migration(startVersion, endVersion) {
            override fun migrate(db: SupportSQLiteDatabase) {
                migration(db)
            }
        }

    private var _migrations = listOf(
        createMigration(1, 2) {
            it.execSQL("ALTER TABLE Reminder RENAME TO ReminderEntity")
        },
        createMigration(2, 1) {
            it.execSQL("ALTER TABLE ReminderEntity RENAME TO Reminder")
        },
    )
    var migrations = _migrations.toTypedArray()
}