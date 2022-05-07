package com.lgtm.emodi.utils

import androidx.annotation.IntRange
import java.util.*

private val MONTH_IN_ENGLISH: Array<String> =
    arrayOf("NONE", "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

private val DAY_OF_WEEK_IN_KOREAN: Array<String> =
    arrayOf("NONE", "일", "월", "화", "수", "목", "금", "토")

object CalendarUtil {

    fun monthInEnglish(@IntRange(from = 1, to = 12) month: Int): String {
        return MONTH_IN_ENGLISH[month]
    }

    fun dayOfWeekInEnglish(@IntRange(from = 1, to = 7) index: Int): String {
        return DAY_OF_WEEK_IN_KOREAN[index]
    }

    fun getCurrentTimeInMills(): Long {
        return Calendar.getInstance().timeInMillis
    }

}