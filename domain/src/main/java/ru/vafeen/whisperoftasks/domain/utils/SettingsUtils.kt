package ru.vafeen.whisperoftasks.domain.utils


import androidx.compose.ui.graphics.Color
import ru.vafeen.whisperoftasks.domain.domain_models.Settings

fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor