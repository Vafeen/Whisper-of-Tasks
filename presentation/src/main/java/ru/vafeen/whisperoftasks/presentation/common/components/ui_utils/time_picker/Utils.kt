package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp

// Количество видимых элементов в столбце
internal const val countOfVisibleItemsInPicker = 5

// Высота одного элемента
internal const val itemHeight = 49

// Высота списка
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