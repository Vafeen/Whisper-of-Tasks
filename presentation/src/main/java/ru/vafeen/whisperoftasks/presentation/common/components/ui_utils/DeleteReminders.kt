package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeleteReminders(
    onDeleteCallback: () -> Unit,
) {
    val deleteColor = Color.Red
    val textToHold = stringResource(id = R.string.hold)
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(deleteColor)
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(context, textToHold, Toast.LENGTH_SHORT)
                        .show()
                },
                onLongClick = {
                    onDeleteCallback()
                }
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .size(30.dp),
            painter = painterResource(R.drawable.delete_forever),
            tint = deleteColor.suitableColor(),
            contentDescription = stringResource(R.string.delete_selected)
        )
    }
}