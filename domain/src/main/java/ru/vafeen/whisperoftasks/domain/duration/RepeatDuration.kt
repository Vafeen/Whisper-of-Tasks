package ru.vafeen.whisperoftasks.domain.duration

import ru.vafeen.whisperoftasks.resources.R
import java.time.Duration

enum class RepeatDuration(val duration: Duration, val resourceName: Int) {
    NoRepeat(duration = Duration.ZERO, resourceName = R.string.no_repeat),
    EveryDay(duration = Duration.ofDays(1), resourceName = R.string.every_day),
    EveryWeek(duration = Duration.ofDays(7), resourceName = R.string.every_week);

    companion object {
        val all = listOf(
            NoRepeat,
            EveryDay,
            EveryWeek
        )
    }
}