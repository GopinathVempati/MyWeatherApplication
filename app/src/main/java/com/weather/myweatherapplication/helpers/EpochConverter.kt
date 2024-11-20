package com.weather.myweatherapplication.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object EpochConverter {
    @SuppressLint("SimpleDateFormat")
    fun readTimestamp(timestamp: Long): String {
        val formatter = SimpleDateFormat("hh:mm")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        return formatter.format(calendar.time)
    }

    fun convertDateStringToLong(dateString: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        format.timeZone = TimeZone.getTimeZone("UTC")  // Set timezone if needed
        val date = format.parse(dateString) ?: return 0L // Handle null if parsing fails
        return date.time
    }
}