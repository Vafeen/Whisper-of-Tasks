package ru.vafeen.whisperoftasks.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal data class Colors(
    val mainColor: Color,
    val singleTheme: Color,
    val oppositeTheme: Color,
    val buttonColor: Color,
    val background: Color,
    val delete: Color,
)

private val baseLightPalette = Colors(
    mainColor = mainLightColor,
    singleTheme = Color.White,
    oppositeTheme = Color.Black,
    buttonColor = Color.White,
    background = Color.White,
    delete = Color.Red
)
private val baseDarkPalette = baseLightPalette.copy(
    mainColor = mainDarkColor,
    singleTheme = Color.Black,
    oppositeTheme = Color.White,
    buttonColor = Color(0xFF29292D),
    background = Color(0xFF2D2D31)
)

@Composable
internal fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (!darkTheme) baseLightPalette
    else baseDarkPalette

    CompositionLocalProvider(
        LocalColors provides colors, content = content
    )
}

internal object Theme {
    val colors: Colors
        @ReadOnlyComposable @Composable get() = LocalColors.current
}

private val LocalColors = staticCompositionLocalOf<Colors> {
    error("Composition error")
}