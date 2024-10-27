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
import androidx.compose.ui.text.style.TextOverflow
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
    var firstIndex by remember { mutableStateOf(0) }
    var lastIndex by remember { mutableStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val newValue =
            list[listState.itemForScrollTo(context) + countOfVisibleItemsInPicker / 2].toIntOrNull()
                ?: return@LaunchedEffect
        if (newValue != selectedValue) {
            onValueChange(newValue)
            selectedValue = newValue
        }

        // Обновляем индексы
        firstIndex = listState.firstVisibleItemIndex
        lastIndex = firstIndex + countOfVisibleItemsInPicker - 1
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
                val itemInfo =
                    listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                val centerOffset =
                    (listState.layoutInfo.viewportEndOffset + listState.layoutInfo.viewportStartOffset) / 2f

                // Рассчитываем высоту элемента в зависимости от его положения относительно центра.
                val heightFactor: Float = when {
                    itemInfo != null -> {
                        if (index == firstIndex || index == lastIndex) itemOfFirstAndLastVisible
                        else if (index == firstIndex + 1 || index == lastIndex - 1) itemOfAVGVisible
                        else itemHeight
                    }

                    else -> itemHeight // Если элемент не виден, используем стандартную высоту
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(1f / countOfVisibleItemsInPicker),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .height(heightFactor.dp) // Устанавливаем высоту для внутреннего элемента списка.
                            .graphicsLayer(
                                scaleX = calculateScaleX(
                                    listState,
                                    index
                                ), // Применяем масштаб по X.
                                scaleY = calculateScaleY(
                                    listState,
                                    index
                                ),  // Применяем масштаб по Y.
                                alpha = calculateAlpha(
                                    index,
                                    listState
                                ) // Применяем прозрачность к элементу.
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextForThisTheme(
                            text = it,
                            fontSize = FontSize.medium19,
                            overflow = TextOverflow.Visible,
                        )
                    }
                }
            }
        }
    }
}
