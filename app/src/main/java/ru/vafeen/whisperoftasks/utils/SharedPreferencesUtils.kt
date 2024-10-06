package ru.vafeen.whisperoftasks.utils


import com.google.gson.Gson
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferences
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferencesValue
import ru.vafeen.whisperoftasks.ui.common.components.Settings

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