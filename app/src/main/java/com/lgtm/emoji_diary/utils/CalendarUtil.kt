package com.lgtm.emoji_diary.utils

import androidx.annotation.IntRange
import com.lgtm.emoji_diary.view.edit.SimpleDate
import java.util.*

private val MONTH_IN_ENGLISH: Array<String> =
    arrayOf("NONE", "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

object CalendarUtil {

    fun monthInEnglish(@IntRange(from = 1, to = 12) month: Int): String {
        return MONTH_IN_ENGLISH[month]
    }

    fun getCurrentSimpleDate(): SimpleDate {
        val calendar = GregorianCalendar()
        calendar.time = Date()

        return SimpleDate(
            year = calendar[Calendar.YEAR],
            month = calendar[Calendar.MONTH] + 1,
            day = calendar[Calendar.DAY_OF_MONTH],
            hour = calendar[Calendar.HOUR_OF_DAY],
            minute = calendar[Calendar.MINUTE]
        )
    }

}