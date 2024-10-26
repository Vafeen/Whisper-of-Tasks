package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.Border
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@Composable
internal fun TimeColumnPicker(
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
        val newValue =
            list[listState.itemForScrollTo(context = context) + countOfVisibleItemsInPicker / 2].toIntOrNull()
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
