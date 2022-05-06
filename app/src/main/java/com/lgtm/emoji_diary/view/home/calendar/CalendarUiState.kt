package com.lgtm.emoji_diary.view.home.calendar

import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.SimpleDate

data class CalendarUiState(
    val year: Int = 2022,
    val month: Int = 1,
    val calendarEmojiMap: Map<SimpleDate, Diary> = mapOf(),
    val loadErrorMessage: String? = null,
)
