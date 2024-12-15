package ru.vafeen.whisperoftasks.domain.shared_preferences

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}