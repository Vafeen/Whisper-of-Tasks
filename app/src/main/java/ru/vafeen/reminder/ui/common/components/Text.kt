package ru.vafeen.reminder.ui.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import ru.vafeen.reminder.ui.theme.Theme

@Composable
fun TextForThisTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.oppositeTheme,
        fontSize = fontSize,
    )
}

@Composable
fun TextForOppositeTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.singleTheme,
        fontSize = fontSize,
    )
}