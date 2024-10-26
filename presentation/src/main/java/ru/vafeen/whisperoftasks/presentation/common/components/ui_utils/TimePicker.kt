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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
private const val countOfVisibleItemsInPicker = 5

// Высота одного элемента
private const val itemHeight = 49

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
        mutableStateOf(
            mutableListOf<String>().apply {
                (1..countOfVisibleItemsInPicker / 2).forEach { _ ->
                    add((""))
                }
                (0..367).forEach {
                    add(
                        dateToday.plusDays((it).toLong())
                            .getDateStringWithWeekOfDay(context = context)
                    )
                }
                (1..countOfVisibleItemsInPicker / 2).forEach { _ ->
                    add((""))
                }
            })
    }
    var offset by remember {
        mutableStateOf(
            listState.firstVisibleItemScrollOffset.pixelsToDp(
                context
            ) % itemHeight
        )
    }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newDate = dateToday.plusDays(listState.itemForScrollTo(context = context).toLong())
        offset = listState.firstVisibleItemScrollOffset.pixelsToDp(context) % itemHeight
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

    Column(modifier) {
        Text(offset.toString())
        Box(
            modifier = Modifier.height(listHeight.dp), contentAlignment = Alignment.Center
        ) {
            Border(itemHeight = itemHeight.dp, color = Theme.colors.oppositeTheme)

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(list) { index, dateStr ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(1f / countOfVisibleItemsInPicker),
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
}


@Composable
private fun TimeColumnPicker(
    initialValue: Int, onValueChange: (Int) -> Unit, range: IntRange, modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialValue)
    val list by remember {
        mutableStateOf(
            mutableListOf<String>().apply {
                (1..countOfVisibleItemsInPicker / 2).forEach { _ ->
                    add((""))
                }
                range.forEach {
                    add(it.getTimeDefaultStr())
                }
                (1..countOfVisibleItemsInPicker / 2).forEach { _ ->
                    add((""))
                }
            })
    }
    var selectedValue by remember { mutableIntStateOf(initialValue) }
    var offset by remember {
        mutableStateOf(
            listState.firstVisibleItemScrollOffset.pixelsToDp(
                context
            ) % itemHeight
        )
    }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        offset = listState.firstVisibleItemScrollOffset.pixelsToDp(context) % itemHeight
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
    Column(modifier) {
        Text(offset.toString())
        Box(
            modifier = Modifier.height(listHeight.dp),
            contentAlignment = Alignment.Center
        ) {
            Border(itemHeight = itemHeight.dp, color = Theme.colors.oppositeTheme)

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(space)
            ) {
                itemsIndexed(list) { index, it ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(1f / countOfVisibleItemsInPicker),
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
}
