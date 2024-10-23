package ru.vafeen.whisperoftasks.data.utils


import com.google.gson.Gson
import ru.vafeen.whisperoftasks.data.Settings
import ru.vafeen.whisperoftasks.data.shared_preferences.SharedPreferences
import ru.vafeen.whisperoftasks.data.shared_preferences.SharedPreferencesValue

fun SharedPreferences.getSettingsOrCreateIfNull(): Settings {
    val settings = getFromSharedPreferences {
        getString(SharedPreferencesValue.Settings.key, "").let {
            if (it != "") Gson().fromJson(it, Settings::class.java)
            else null
        }
    }
    return if (settings != null) settings
    else {
        val newSettings = Settings()
        saveInOrRemoveFromSharedPreferences {
            putString(SharedPreferencesValue.Settings.key, newSettings.toJsonString())
        }
        newSettings
    }
}