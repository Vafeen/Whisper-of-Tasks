package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.pixelsToDp
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.itemHeight
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.DatePickerInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Composable
internal fun DateColumnPicker(
    initialDate: LocalDate,
    onValueChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    val context = LocalContext.current
    val initialDaysIndexItem by remember {
        mutableIntStateOf(
            ChronoUnit.DAYS.between(DatePickerInfo.startDateInPast(), selectedDate).toInt()
        )
    }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialDaysIndexItem)
    // Генерация списка дат.
    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
            DatePickerInfo.dateList().forEach {
                add(it.getDateStringWithWeekOfDay(context))
            }
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
        })
    }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newDate = DatePickerInfo.startDateInPast().plusDays(listState.itemForScrollTo(context = context).toLong())
        if (newDate != selectedDate) {
            onValueChange(newDate)
            selectedDate = newDate
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset.pixelsToDp(
                context
            ) % itemHeight != 0f
        ) {
            // Перемотка к центральному элементу
            listState.animateScrollToItem(listState.itemForScrollTo(context = context))
        }
    }

    Box(
        modifier = modifier.height(listHeight.dp),
        contentAlignment = Alignment.Center
    ) {
        Border(
            itemHeight = itemHeight.dp,
            color = Theme.colors.oppositeTheme
        )
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = list) { index, it ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(1f / countOfVisibleItemsInPicker),
                    contentAlignment = Alignment.Center
                ) {
                    TextForThisTheme(
                        modifier = Modifier.graphicsLayer(
                            scaleX = calculateScaleX(listState, index),
                            scaleY = calculateScaleY(listState, index),
                            alpha = calculateAlpha(index, listState)
                        ),
                        text = it,
                        fontSize = FontSize.medium19,
                        textDecoration = if (index < DatePickerInfo.countOfDaysInPast + countOfVisibleItemsInPicker / 2)
                            TextDecoration.LineThrough
                        else TextDecoration.None
                    )
                }
            }
        }
    }

}




