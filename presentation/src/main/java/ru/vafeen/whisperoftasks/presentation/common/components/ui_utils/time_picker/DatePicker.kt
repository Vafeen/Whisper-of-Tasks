import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.data.utils.pixelsToDp
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.Border
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.calculateAlpha
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.calculateScaleX
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.calculateScaleY
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.countOfVisibleItemsInPicker
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.itemForScrollTo
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.itemHeight
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.itemOfAVGVisible
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.itemOfFirstAndLastVisible
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.listHeight
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
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
    val dateToday by remember { mutableStateOf(LocalDate.now()) }
    val initialDaysIndexItem by remember {
        mutableIntStateOf(
            ChronoUnit.DAYS.between(dateToday, selectedDate).toInt()
        )
    }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialDaysIndexItem)

    // Генерация списка дат.
    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
            (0..365).forEach {
                add(dateToday.plusDays(it.toLong()).getDateStringWithWeekOfDay(context))
            }
            (1..(countOfVisibleItemsInPicker / 2)).forEach { _ -> add("") }
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
            itemsIndexed(items = list) { index, dateStr ->
                // Рассчитываем высоту элемента в зависимости от его положения относительно центра.
                val heightFactor: Float = when {
                    index == 0 || index == list.size - 1 -> itemOfFirstAndLastVisible
                    index == 1 || index == list.size - 2 -> itemOfAVGVisible
                    else -> itemHeight
                }

                Box(modifier = Modifier.fillParentMaxHeight(1f / countOfVisibleItemsInPicker)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightFactor.dp)
                            .graphicsLayer(
                                scaleY = calculateScaleY(
                                    listState,
                                    index
                                ), // Используем новую функцию для Y
                                scaleX = calculateScaleX(
                                    listState,
                                    index
                                ), // Используем новую функцию для X
                                alpha = calculateAlpha(
                                    index,
                                    listState
                                ) // Применяем прозрачность к элементу.
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dateStr,
                            fontSize = FontSize.medium19,
                            overflow = TextOverflow.Visible,
                        )
                    }
                }
            }
        }
    }

}




