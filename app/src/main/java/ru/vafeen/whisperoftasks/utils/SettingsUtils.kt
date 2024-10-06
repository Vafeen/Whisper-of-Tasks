package ru.vafeen.whisperoftasks.utils

import androidx.compose.ui.graphics.Color
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferences
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferencesValue
import ru.vafeen.whisperoftasks.ui.common.components.Settings


fun Settings.save(
    sharedPreferences: SharedPreferences,
): Settings {
    sharedPreferences.saveInOrRemoveFromSharedPreferences {
        putString(SharedPreferencesValue.Settings.key, this@save.toJsonString())
    }
    return this
}

fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor