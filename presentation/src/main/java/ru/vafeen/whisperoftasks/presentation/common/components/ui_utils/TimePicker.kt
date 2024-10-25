package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.data.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

// Количество видимых элементов в столбце
private const val countOfVisibleItemsInPicker = 3

// Высота одного элемента
private const val itemHeight = 50

// Высота списка
private const val listHeight = countOfVisibleItemsInPicker * itemHeight

private fun LazyListState.itemForScrollTo(context: Context): Int {
    val offset = firstVisibleItemScrollOffset.pixelsToDp(context)
    return when {
        offset == 0f -> firstVisibleItemIndex
        offset % itemHeight >= itemHeight / 2 -> firstVisibleItemIndex + 1
//        offset % itemHeight < itemHeight / 2 -> firstVisibleItemIndex
        else -> firstVisibleItemIndex
    }
}


@Composable
fun Border(itemHeight: Dp, color: Color) {
    val width = 2.dp
    val strokeWidthPx = with(LocalDensity.current) { width.toPx() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .drawBehind {
                drawLine(
                    color = color,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f) // Верхняя граница
                )
                drawLine(
                    color = color,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height) // Нижняя граница
                )
            }
    ) {}
}


@Composable
fun MyDateTimePicker(
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

@Composable
private fun DateColumnPicker(
    initialDate: LocalDate,
    onValueChange: (LocalDate?) -> Unit, modifier: Modifier = Modifier,
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    val context = LocalContext.current
    val dateToday by remember { mutableStateOf(LocalDate.now()) }
    val initialDaysIndexItem by remember {
        mutableStateOf(
            ChronoUnit.DAYS.between(
                dateToday,
                selectedDate
            ).toInt()
        )
    }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialDaysIndexItem
    )
    val list by remember {
        mutableStateOf(List(size = 367) {
            return@List when (it) {
                0 -> ""
                366 -> ""
                else -> dateToday.plusDays((it - 1).toLong())
                    .getDateStringWithWeekOfDay(context = context)
            }
        })
    }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newDate = dateToday.plusDays(listState.itemForScrollTo(context = context).toLong())
        if (newDate != selectedDate) {
            onValueChange(newDate)
            selectedDate = newDate
        }
    }


    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset.pixelsToDp(
                context
            ) % itemHeight != 0f // иначе будет постоянная рекомпозиция
        ) {
            // Перемотка к центральному элементу
            listState.animateScrollToItem(listState.itemForScrollTo(context = context))
        }
    }
    Box(
        modifier = modifier.height(listHeight.dp), contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = itemHeight.dp, color = Theme.colors.oppositeTheme)

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = list) { dateStr ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(1 / 3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextForThisTheme(
                        text = dateStr,
                        fontSize = FontSize.medium19,
                    )
                }
            }
        }
    }
}


@Composable
private fun TimeColumnPicker(
    initialValue: Int, onValueChange: (Int) -> Unit, range: IntRange, modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialValue)
    val list by remember {
        mutableStateOf(mutableListOf("").apply {
            for (i in range) add(i.getTimeDefaultStr())
            add("")
        })
    }

    var selectedValue by remember { mutableIntStateOf(initialValue) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newValue = list[listState.itemForScrollTo(context = context) + 1].toIntOrNull()
        if (newValue != null && newValue != selectedValue) {
            onValueChange(newValue)
            selectedValue = newValue
        }
    }
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset.pixelsToDp(
                context
            ) % itemHeight != 0f // иначе будет постоянная рекомпозиция
        ) {
            // Перемотка к центральному элементу
            listState.animateScrollToItem(listState.itemForScrollTo(context = context))
        }
    }
    Box(
        modifier = modifier.height(listHeight.dp), contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = itemHeight.dp, color = Theme.colors.oppositeTheme)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(items = list) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(1 / 3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextForThisTheme(
                        text = it,
                        fontSize = FontSize.medium19,
                    )
                }
            }
        }
    }
}
