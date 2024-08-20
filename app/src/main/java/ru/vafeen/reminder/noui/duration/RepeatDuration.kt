package ru.vafeen.reminder.noui.duration

enum class RepeatDuration(val duration: MyDuration) {
    NoRepeat(duration = MyDuration(0)),
    EveryDay(duration = MyDuration.ofTime(days = 1)),
    EveryWeek(duration = MyDuration.ofTime(days = 7));

    companion object {
        val all = listOf(
            NoRepeat,
            EveryDay,
            EveryWeek
        )
    }
}