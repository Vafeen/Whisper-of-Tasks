package ru.vafeen.whisperoftasks.ui.common.components.ui_utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.ui.theme.FontSize
import ru.vafeen.whisperoftasks.ui.theme.Theme
import ru.vafeen.whisperoftasks.utils.getDateString
import ru.vafeen.whisperoftasks.utils.getTimeDefaultStr
import java.time.LocalDate
import java.time.LocalTime


@Composable
fun Border(itemHeight: Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .background(Theme.colors.oppositeTheme)
            .padding(top = 2.dp, bottom = 2.dp)
            .background(Theme.colors.singleTheme)
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
    var pickedTime by remember {
        mutableStateOf(initialTime)
    }
    var pickedDate by remember {
        mutableStateOf(initialDate)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(width = 2.dp, color = Theme.colors.oppositeTheme)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (initialDate != null && pickedDate != null)
            DateColumnPicker(modifier = Modifier.weight(1f), onValueChange = {
                if (it != null) {
                    pickedDate = it
                    onDateSelected(it)
                }
            })
        if (isTimeNeeded && initialTime != null && pickedTime != null) {
            TimeColumnPicker(
                modifier = Modifier.weight(2f),
                value = pickedTime?.hour ?: 0,
                onValueChange = { hour ->
                    if (hour != null) {
                        pickedTime = LocalTime.of(hour, pickedTime?.minute ?: 0)
                        pickedTime?.let { onTimeSelected(it) }
                    }
                },
                range = 0..23,
            )
            TimeColumnPicker(
                value = pickedTime?.minute ?: 0, onValueChange = { minute ->
                    if (minute != null) {
                        pickedTime = LocalTime.of(pickedTime?.hour ?: 0, minute)
                        pickedTime?.let { onTimeSelected(it) }
                    }
                }, range = 0..59, modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DateColumnPicker(
    onValueChange: (LocalDate?) -> Unit, modifier: Modifier = Modifier,
) {
    // Высота одного элемента
    val itemHeight = 40.dp
    // Количество видимых элементов в столбце
    val size = 3
    // Отступ между элементами
    val space = 24.dp
    // Высота списка (должна вмещать ровно три элемента)
    val listHeight = itemHeight * size + space * 2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val dateToday = LocalDate.now()
    val list = mutableListOf("")
    for (i in 0..365) {
        list.add(dateToday.plusDays((i).toLong()).getDateString())
    }
    list.add("")
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            // Перемотка к центральному элементу
            listState.animateScrollToItem(listState.firstVisibleItemIndex)
        }
    }
    Box(
        modifier = modifier.height(listHeight), contentAlignment = Alignment.Center
    ) {

        Border(itemHeight = itemHeight)


        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space)
        ) {
            itemsIndexed(list) { index, it ->
                val newDT = dateToday.plusDays(index.toLong() - 1)
                if (listState.firstVisibleItemIndex == index - 1) onValueChange(newDT)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextForThisTheme(
                        text = it,
                        fontSize = FontSize.medium19,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeColumnPicker(
    value: Int, onValueChange: (Int?) -> Unit, range: IntRange, modifier: Modifier = Modifier,
) {
    // Высота одного элемента
    val itemHeight = 40.dp
    // Количество видимых элементов в столбце
    val size = 3
    // Отступ между элементами
    val space = 24.dp
    // Высота списка (должна вмещать ровно три элемента)
    val listHeight = itemHeight * size + space * 2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = value)
    val firstIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val list = mutableListOf("")
    for (i in range) list.add(i.getTimeDefaultStr())
    list.add("")

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            // Перемотка к центральному элементу
            listState.animateScrollToItem(listState.firstVisibleItemIndex)
        }
    }

    Box(
        modifier = modifier.height(listHeight), contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = itemHeight)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space)
        ) {
            itemsIndexed(list) { index, it ->
                val isSelected by remember { mutableStateOf(firstIndex.value == index - 1) }
                if (isSelected) onValueChange(list[firstIndex.value + 1].toIntOrNull())
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextForThisTheme(text = it, fontSize = FontSize.medium19)
                }
            }
        }
    }
}


