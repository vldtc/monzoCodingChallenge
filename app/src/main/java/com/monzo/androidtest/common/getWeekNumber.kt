package com.monzo.androidtest.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getWeekNumber(date: Date): String{

    val calendar = Calendar.getInstance()
    val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)
    calendar.time = date
    val articleWeek = calendar.get(Calendar.WEEK_OF_YEAR)

    return when{
        currentWeek == articleWeek -> "This week"
        currentWeek - articleWeek == 1 -> "Last week"
        else -> "Week $articleWeek"
    }

}