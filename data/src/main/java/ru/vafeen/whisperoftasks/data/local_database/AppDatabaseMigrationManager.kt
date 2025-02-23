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
        createMigration(startVersion = 1, endVersion = 2) { db ->
            db.execSQL("ALTER TABLE Reminder ADD COLUMN isDeleted INTEGER DEFAULT 0 NOT NULL")
        }
    )
    var migrations = _migrations.toTypedArray()
}