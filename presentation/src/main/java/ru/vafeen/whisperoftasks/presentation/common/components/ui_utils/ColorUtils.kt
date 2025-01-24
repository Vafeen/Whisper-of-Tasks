package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@Composable
internal fun Settings.customMainColorOrDefault(isSystemInDarkTheme: Boolean): Color =
    (if (isSystemInDarkTheme) darkThemeColor else lightThemeColor) ?: Theme.colors.mainColor
