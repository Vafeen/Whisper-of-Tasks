package ru.vafeen.whisperoftasks.presentation.components.main_screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TODOWithReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.DatePickerInfo
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MainScreen(bottomBarNavigator: BottomBarNavigator) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.customMainColorOrDefault()

    val cor = rememberCoroutineScope()
    var localTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var localDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var isEditingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> =
        remember {
            mutableStateOf(null)
        }
    val isDeletingInProcess by remember {
        derivedStateOf {
            viewModel.selectedReminders.isNotEmpty()
        }
    }

    fun Modifier.combinedClickableForRemovingReminder(reminder: Reminder): Modifier =
        this.combinedClickable(
            onClick = {
                if (!isDeletingInProcess) {
                    lastReminder.value = reminder
                    isEditingReminder = true
                } else {
                    viewModel.changeStatusForDeleting(reminder)
                }
            },
            onLongClick = {
                if (!isDeletingInProcess) {
                    viewModel.changeStatusForDeleting(reminder)
                } else {
                    viewModel.clearSelectedReminders()
                }
            }
        )


    val reminders by viewModel.remindersFlow.collectAsState(listOf())
    val cardsWithDateState =
        rememberLazyListState(initialFirstVisibleItemIndex = DatePickerInfo.countOfDaysInPast - 1)

    val pagerState = rememberPagerState(
        pageCount = {
            DatePickerInfo.pageNumber
        }, initialPage = DatePickerInfo.countOfDaysInPast
    )

    BackHandler {
        when {
            isDeletingInProcess -> {
                viewModel.clearSelectedReminders()
            }

            !isDeletingInProcess && pagerState.currentPage == DatePickerInfo.countOfDaysInPast -> {
                bottomBarNavigator.back()
                (context as Activity).finish()
            }

            else -> {
                cor.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(DatePickerInfo.countOfDaysInPast)
                }
            }
        }
    }

    LaunchedEffect(key1 = null) {
        withContext(Dispatchers.Main) {
            while (true) {
                localTime = LocalTime.now()
                delay(timeMillis = 1000L)
            }
        }
    }
    LaunchedEffect(key1 = pagerState.currentPage) {
        cardsWithDateState.animateScrollToItem(
            if (pagerState.currentPage > 0) pagerState.currentPage - 1
            else pagerState.currentPage
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Theme.colors.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextForThisTheme(
                    text = stringResource(id = R.string.tasks_by_day),
                    fontSize = FontSize.huge27
                )
            }
            if (isEditingReminder && lastReminder.value != null) {
                ReminderDialog(
                    newReminder = lastReminder as MutableState<Reminder>, // safety is above
                    onDismissRequest = { isEditingReminder = false }
                )
            }

            LazyRow(
                state = cardsWithDateState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                itemsIndexed(DatePickerInfo.dateList()) { index, day ->
                    Column {
                        Card(
                            modifier = Modifier
                                .fillParentMaxWidth(1 / 3f)
                                .padding(horizontal = 5.dp)
                                .clickable {
                                    cor.launch(Dispatchers.Main) {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (day == viewModel.todayDate) mainColor
                                else Theme.colors.buttonColor,
                                contentColor = (if (day == viewModel.todayDate) mainColor
                                else Theme.colors.buttonColor).suitableColor()
                            ), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                        ) {
                            Text(
                                text = day.getDateStringWithWeekOfDay(context = context),
                                fontSize = FontSize.small17,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 5.dp, horizontal = 10.dp
                                    ),
                                textAlign = TextAlign.Center
                            )
                        }
                        if (day == localDate)
                            Card(
                                modifier = Modifier
                                    .fillParentMaxWidth(1 / 3f)
                                    .padding(vertical = 2.dp)
                                    .padding(horizontal = 18.dp)
                                    .height(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Theme.colors.oppositeTheme)
                            ) {}
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(10f)
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val startDateInPast = DatePickerInfo.startDateInPast()
                    val dateOfThisPage = startDateInPast.plusDays(page.toLong())
                    localDate =
                        startDateInPast.plusDays(pagerState.currentPage.toLong())
                    var primaryReminders by remember {
                        mutableStateOf(listOf<Reminder>())
                    }
                    var lostReminders by remember {
                        mutableStateOf(listOf<Reminder>())
                    }
                    if (primaryReminders.isEmpty() && lostReminders.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextForThisTheme(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.no_events_today),
                                fontSize = FontSize.big22
                            )
                        }
                    }
                    LaunchedEffect(reminders) {
                        launch(Dispatchers.Main) {
                            primaryReminders = reminders.filter {
                                val d = it.dt.toLocalDate()
                                d == dateOfThisPage ||
                                        it.repeatDuration == RepeatDuration.EveryDay && d <= dateOfThisPage ||
                                        it.repeatDuration == RepeatDuration.EveryWeek && it.dt.dayOfWeek == dateOfThisPage.dayOfWeek
                            }
                        }
                        launch(Dispatchers.Main) {
                            lostReminders = reminders.filter {
                                it.repeatDuration == RepeatDuration.NoRepeat &&
                                        dateOfThisPage > it.dt.toLocalDate()
                            }
                        }
                    }
                    LazyColumn {
                        stickyHeader {
                            if (primaryReminders.isNotEmpty()) {
                                TextForThisTheme(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Theme.colors.singleTheme),
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.primary_events),
                                    fontSize = FontSize.medium19
                                )
                            }
                        }
                        items(primaryReminders) { it ->
                            it.ReminderDataString(
                                mainColor = mainColor,
                                modifier = Modifier.combinedClickableForRemovingReminder(
                                    reminder = it
                                ),
                                setEvent = viewModel::setEvent,
                                dateOfThisPage = dateOfThisPage,
                                isItCandidateForDelete = viewModel.selectedReminders.contains(
                                    it.idOfReminder
                                ),
                                changeStatusOfSelecting = if (isDeletingInProcess) {
                                    { viewModel.changeStatusForDeleting(it) }
                                } else null,
                                showNotification = viewModel::showNotification
                            )
                        }
                        stickyHeader {
                            if (lostReminders.isNotEmpty()) {
                                TextForThisTheme(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Theme.colors.singleTheme),
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.past_events),
                                    fontSize = FontSize.medium19
                                )
                            }
                        }
                        items(lostReminders) {
                            it.ReminderDataString(
                                mainColor = mainColor,
                                modifier = Modifier.combinedClickableForRemovingReminder(
                                    reminder = it
                                ),
                                setEvent = viewModel::setEvent,
                                dateOfThisPage = dateOfThisPage,
                                isItCandidateForDelete = viewModel.selectedReminders.contains(
                                    it.idOfReminder
                                ),
                                changeStatusOfSelecting = if (isDeletingInProcess) {
                                    { viewModel.changeStatusForDeleting(it) }
                                } else null,
                                showNotification = viewModel::showNotification
                            )
                        }
                    }
                }
            }
            if (isDeletingInProcess) TODOWithReminders(
                actionName = R.string.mv_to_trash_selected,
                actionColor = Theme.colors.delete,
                actionTextColor = Color.Black,
            ) {
                viewModel.moveToTrashSelectedReminders()
            }
        }
    }
}