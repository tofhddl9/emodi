package com.lgtm.emoji_diary.view.edit

import android.os.Parcelable
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleDate(
    val year : Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val dayOfWeek: Int = 1,
    val hourOfDay: Int = 0,
    val minute: Int = 0,
) : Parcelable

fun SimpleDate.asDateFormat(): String {
    val mm = "${month+1}".padStart(2, '0')
    val dd = "$day".padStart(2, '0')

    return "$year-$mm-$dd"
}

fun makeDateFormatToSimpleDate(dateFormat: String): SimpleDate {
    val (year, month, day) = dateFormat.split('-')
    return SimpleDate(year = year.toInt(), month = month.toInt()-1, day = day.toInt())
}

fun SimpleDate.asTimeFormat(): String {
    val hh = "$hourOfDay".padStart(2, '0')
    val mm = "$minute".padStart(2, '0')

    return "$hh : $mm"
}

fun makeTimeFormatToSimpleDate(timeFormat: String): SimpleDate {
    val (hour, minute) = timeFormat.replace(" ","").split(':')
    return SimpleDate(hourOfDay = hour.toInt(), minute = minute.toInt())
}

fun SimpleDate.asTimeInMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hourOfDay, minute)

    return calendar.timeInMillis
}

fun SimpleDate.isSunday() = dayOfWeek == 1

fun SimpleDate.isSaturday() = dayOfWeek == 7

fun Calendar.asSimpleDate() = SimpleDate(
    year = get(Calendar.YEAR),
    month = get(Calendar.MONTH),
    day = get(Calendar.DAY_OF_MONTH),
    dayOfWeek = get(Calendar.DAY_OF_WEEK),
    hourOfDay = get(Calendar.HOUR_OF_DAY),
    minute = get(Calendar.MINUTE),
)

fun getCurrentSimpleDate() = Calendar.getInstance().asSimpleDate()

fun timeInMillisToSimpleDate(timeInMillis: Long): SimpleDate {
    return Calendar.getInstance().apply {
        this.timeInMillis = timeInMillis
    }.asSimpleDate()
}
