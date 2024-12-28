package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@Composable
internal fun TimeColumnPicker(
    initialValue: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialValue)

    // Генерация списка значений времени.
    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
            for (i in range) add(i.getTimeDefaultStr())
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
        })
    }

    var selectedValue by remember { mutableIntStateOf(initialValue) }
//    var firstIndex by remember { mutableStateOf(0) }
//    var lastIndex by remember { mutableStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newValue =
            list[listState.itemForScrollTo(context) + countOfVisibleItemsInPicker / 2].toIntOrNull()
        if (newValue != null && newValue != selectedValue) {
            onValueChange(newValue)
            selectedValue = newValue
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
        Border(itemHeight = itemHeight.dp, color = Theme.colors.oppositeTheme)

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = list) { index, it ->
                Box(
                    modifier = Modifier
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
                    )
                }
            }
        }
    }
}