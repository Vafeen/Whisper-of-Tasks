package ru.vafeen.whisperoftasks.data.shared_preferences

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}