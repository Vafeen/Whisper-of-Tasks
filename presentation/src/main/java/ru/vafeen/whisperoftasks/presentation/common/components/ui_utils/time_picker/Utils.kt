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
import ru.vafeen.whisperoftasks.domain.utils.pixelsToDp
import kotlin.math.abs

// Количество видимых элементов в столбце.
internal const val countOfVisibleItemsInPicker = 5

// Высота стандартного элемента.
internal const val itemHeight = 35f

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
    // Получаем информацию о текущем состоянии компоновки списка
    val layoutInfo = listState.layoutInfo
    // Извлекаем индексы видимых элементов
    val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
    // Если элемент не виден, возвращаем масштаб 1 (нормальный)
    if (!visibleItems.contains(index)) return 1f
    // Находим информацию о конкретном элементе по индексу
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
    // Вычисляем центр видимой области
    val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
    // Вычисляем расстояние от центра до середины элемента
    val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
    // Максимальное расстояние до центра для расчета масштаба
    val maxDistance = layoutInfo.viewportEndOffset / 2f
    // Сжимаем элемент до половины при максимальном расстоянии
    return 1f - (distance / maxDistance) * 0.5f
}

// Функция для расчета масштаба элемента по оси Y на основе его положения в списке относительно центра видимой области.
internal fun calculateScaleY(listState: LazyListState, index: Int): Float {
    // Получаем информацию о текущем состоянии компоновки списка
    val layoutInfo = listState.layoutInfo
    // Извлекаем индексы видимых элементов
    val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
    // Если элемент не виден, возвращаем масштаб 1 (нормальный)
    if (!visibleItems.contains(index)) return 1f
    // Находим информацию о конкретном элементе по индексу
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
    // Вычисляем центр видимой области
    val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
    // Вычисляем расстояние от центра до середины элемента
    val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
    // Максимальное расстояние до центра для расчета масштаба
    val maxDistanceY = layoutInfo.viewportEndOffset / 2f
    // Сжимаем элемент полностью при максимальном расстоянии
    return 1f - (distance / maxDistanceY)
}

// Функция для расчета прозрачности на основе индекса элемента, общего количества элементов и состояния списка.
fun calculateAlpha(index: Int, listState: LazyListState): Float {
    // Получаем информацию о текущем состоянии компоновки списка
    val layoutInfo = listState.layoutInfo
    // Извлекаем индексы видимых элементов
    val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
    // Если нет видимых элементов, возвращаем максимальную непрозрачность
    if (visibleItems.isEmpty()) return 1f
    // Вычисляем центр видимой области
    val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
    // Находим информацию о конкретном элементе по индексу
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
    // Вычисляем расстояние от центра до середины элемента
    val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
    // Максимальное расстояние для расчета прозрачности
    val maxDistance = layoutInfo.viewportEndOffset / 2f
    // Уменьшаем прозрачность до 0.3 при максимальном расстоянии
    return 1f - (distance / maxDistance) * 0.7f
}