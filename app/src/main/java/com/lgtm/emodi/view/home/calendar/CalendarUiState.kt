package com.lgtm.emodi.view.home.calendar

import com.lgtm.emodi.data.Diary
import com.lgtm.emodi.data.SimpleDate

data class CalendarUiState(
    val year: Int = 2022,
    val month: Int = 1,
    val calendarEmojiMap: Map<SimpleDate, Diary> = mapOf(),
    val loadErrorMessage: String? = null,
)
