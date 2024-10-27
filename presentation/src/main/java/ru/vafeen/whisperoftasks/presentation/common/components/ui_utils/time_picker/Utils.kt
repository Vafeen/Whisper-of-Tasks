package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp
import kotlin.math.abs

// Количество видимых элементов в столбце.
internal const val countOfVisibleItemsInPicker = 5

// Высота стандартного элемента.
internal const val itemHeight = 40f

// Высота для элементов, находящихся рядом с центральным элементом (например, второй и предпоследний).
internal const val itemOfAVGVisible = 30f

// Минимальная высота для первого и последнего видимых элементов.
internal const val itemOfFirstAndLastVisible = 20f

// Высота списка.
internal const val listHeight = countOfVisibleItemsInPicker * itemHeight

internal fun LazyListState.itemForScrollTo(context: Context): Int {
    val offset = firstVisibleItemScrollOffset.pixelsToDp(context)
    return when {
        offset == 0f -> firstVisibleItemIndex
        offset % itemHeight >= itemHeight / 2 -> firstVisibleItemIndex + 1
//        offset % itemHeight < itemHeight / 2 -> firstVisibleItemIndex
        else -> firstVisibleItemIndex
    }
}

@Composable
internal fun Border(itemHeight: Dp, color: Color) {
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
                    end = Offset(size.width, 0f)
                )
                drawLine(
                    color = color,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
    ) {}
}


// Функция для расчета масштаба элемента по оси X на основе его положения в списке относительно центра видимой области.
internal fun calculateScaleX(listState: LazyListState, index: Int): Float {
    val layoutInfo = listState.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
    if (!visibleItems.contains(index)) return 1f

    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
    val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
    val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)

    val maxDistance = layoutInfo.viewportEndOffset / 2f
    // Сжимаем элемент до половины при максимальном расстоянии
    return 1f - (distance / maxDistance) * 0.5f
}

// Функция для расчета масштаба элемента по оси Y на основе его положения в списке относительно центра видимой области.
internal fun calculateScaleY(listState: LazyListState, index: Int): Float {
    val layoutInfo = listState.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
    if (!visibleItems.contains(index)) return 1f

    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
    val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
    val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)

    // Максимальное расстояние до центра, чтобы элемент стал почти невидимым
    val maxDistanceY = layoutInfo.viewportEndOffset / 2f

    // Применяем сильное уменьшение масштаба по оси Y
    return if (distance < maxDistanceY) {
        1f - (distance / maxDistanceY) * 0.9f // Уменьшаем до 10% при максимальном расстоянии
    } else {
        0.1f // Минимальный масштаб, когда элемент далеко от центра
    }
}


// Функция для расчета прозрачности на основе индекса элемента, общего количества элементов и состояния списка.
fun calculateAlpha(index: Int, listState: LazyListState): Float {
    val visibleItems = listState.layoutInfo.visibleItemsInfo.map { it.index }

    return if (visibleItems.isNotEmpty()) when (// Если элемент является первым или последним видимым элементом
        index) {
        visibleItems.first(), visibleItems.last() -> 0.3f // Минимальная непрозрачность для крайних элементов
        // Если элемент является вторым или предпоследним видимым элементом
        visibleItems[1], visibleItems[visibleItems.size - 2] -> 0.6f // Средняя непрозрачность для второго и предпоследнего
        else -> 1f // Остальные элементы менее прозрачные
    } else 1f
}