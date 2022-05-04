package com.lgtm.emoji_diary.view.home.calendar

import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.view.edit.SimpleDate

data class CalendarUiState(
    val year: Int = 2022,
    val month: Int = 1,
    val calendarEmojiMap: Map<SimpleDate, Diary> = mapOf(),
    val loadErrorMessage: String? = null,
)

// Y-M-D 날짜를 구해서 Calendar 의 리스트를 관리
// 이걸 캘린더뷰에 넘겨줌

// onResume 때마다 리프레시
