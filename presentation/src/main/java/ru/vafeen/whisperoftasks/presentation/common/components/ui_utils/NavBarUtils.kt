package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetNavigationBarColor(color: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = color.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                true // или false в зависимости от вашего дизайна
        }
    }
}