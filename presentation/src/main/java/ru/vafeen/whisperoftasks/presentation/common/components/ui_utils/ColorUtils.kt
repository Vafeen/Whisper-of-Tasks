package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@Composable
internal fun Settings.customMainColorOrDefault(): Color =
    (if (isSystemInDarkTheme()) darkThemeColor else lightThemeColor) ?: Theme.colors.mainColor
