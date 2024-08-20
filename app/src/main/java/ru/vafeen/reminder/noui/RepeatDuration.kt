package ru.vafeen.reminder.noui

import java.time.Duration

enum class RepeatDuration(val duration: Duration) {
    NoRepeat(duration = Duration.ZERO),
    EveryDay(duration = Duration.ofDays(1)),
    EveryWeek(duration = Duration.ofDays(7)),
    EveryMonth(duration = Duration.ofDays(30));

    companion object {
        val all = listOf(
            NoRepeat,
            EveryDay,
            EveryWeek,
            EveryMonth,
        )
    }
}