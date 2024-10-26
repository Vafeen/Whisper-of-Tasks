package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@Composable
fun TextForThisTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.oppositeTheme,
        fontSize = fontSize,
        textAlign = textAlign,
        style = style,
        overflow = overflow,
        maxLines = 1
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