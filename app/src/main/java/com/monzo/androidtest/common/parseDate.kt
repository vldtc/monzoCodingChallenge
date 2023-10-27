package com.monzo.androidtest.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun parseDate(date: Date): String{
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(date)
}
