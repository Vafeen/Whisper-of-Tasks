package ru.vafeen.reminder.noui.duration

class MyDuration(val seconds: Long) {
    companion object {
        fun ofTime(
            seconds: Long = 0,
            minutes: Long = 0,
            hours: Long = 0,
            days: Long = 0
        ): MyDuration = MyDuration(seconds = seconds + minutes * 60 + hours * 3600 + days * 86400)

    }

    operator fun plus(myDuration: MyDuration): MyDuration =
        MyDuration(seconds = seconds + myDuration.seconds)

    operator fun minus(myDuration: MyDuration): MyDuration =
        MyDuration(seconds = if (seconds > myDuration.seconds) seconds - myDuration.seconds else 0)
}