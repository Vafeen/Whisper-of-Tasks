package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import DateColumnPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun MyDateTimePicker(
    isTimeNeeded: Boolean,
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    initialTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(width = 2.dp, color = Theme.colors.oppositeTheme)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (initialDate != null)
            DateColumnPicker(modifier = Modifier.weight(1f),
                initialDate = initialDate,
                onValueChange = {
                    if (it != null) {
                        onDateSelected(it)
                    }
                })
        if (isTimeNeeded && initialTime != null) {
            Row(modifier = Modifier.weight(1f)) {
                TimeColumnPicker(
                    modifier = Modifier.weight(1f),
                    initialValue = initialTime.hour,
                    onValueChange = { hour ->
                        onTimeSelected(initialTime.withHour(hour))
                    },
                    range = 0..23,
                )
                TimeColumnPicker(
                    modifier = Modifier.weight(1f),
                    initialValue = initialTime.minute,
                    onValueChange = { minute ->
                        onTimeSelected(initialTime.withMinute(minute))
                    },
                    range = 0..59,
                )
            }
        }
    }
}