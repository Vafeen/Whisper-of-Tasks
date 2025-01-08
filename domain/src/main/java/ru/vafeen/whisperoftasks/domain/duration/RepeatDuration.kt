package ru.vafeen.whisperoftasks.domain.duration

import ru.vafeen.whisperoftasks.resources.R

enum class RepeatDuration(val duration: MyDuration, val resourceName: Int) {
    NoRepeat(duration = MyDuration(0), resourceName = R.string.no_repeat),
    EveryDay(duration = MyDuration.ofTime(days = 1), resourceName = R.string.every_day),
    EveryWeek(duration = MyDuration.ofTime(days = 7), resourceName = R.string.every_week);

    companion object {
        val all = listOf(
            NoRepeat,
            EveryDay,
            EveryWeek
        )
    }
}