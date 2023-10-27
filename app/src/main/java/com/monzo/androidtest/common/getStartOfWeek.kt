package com.monzo.androidtest.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.N)
fun getStartOfWeek(articlePublishDate: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = articlePublishDate
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return calendar.time
}