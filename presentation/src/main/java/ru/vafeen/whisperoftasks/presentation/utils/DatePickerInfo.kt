package ru.vafeen.whisperoftasks.presentation.utils

import java.time.LocalDate

object DatePickerInfo {
    const val countOfDaysInPast = 30
    const val pageNumber = 365
    fun startDateInPast(): LocalDate = LocalDate.now().minusDays(countOfDaysInPast.toLong())
    fun dateList(): List<LocalDate> {
        val startDateInPast = startDateInPast()
        val dateList = mutableListOf<LocalDate>()
        (0..pageNumber).forEach {
            dateList.add(startDateInPast.plusDays(it.toLong()))
        }
        return dateList
    }
}