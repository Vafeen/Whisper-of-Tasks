package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.resources.R

@Composable
fun ListGridChangeView(
    isListChosen: Boolean,
    changeToList: () -> Unit, changeToGrid: () -> Unit
) {
    val modifierForBorder =
        Modifier
            .border(BorderStroke(width = 2.dp, color = Theme.colors.oppositeTheme))
            .padding(2.dp)
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = changeToList) {
            Icon(
                modifier = Modifier.let {
                    if (isListChosen) it.then(modifierForBorder)
                    else it
                },
                painter = painterResource(R.drawable.list),
                contentDescription = context.getString(R.string.view_reminders_as_list),
                tint = Theme.colors.oppositeTheme
            )
        }
        IconButton(onClick = changeToGrid) {
            Icon(
                modifier = Modifier.let {
                    if (!isListChosen) it.then(modifierForBorder)
                    else it
                },
                painter = painterResource(R.drawable.grid),
                contentDescription = context.getString(R.string.view_reminders_as_grid),
                tint = Theme.colors.oppositeTheme
            )
        }
    }
}
