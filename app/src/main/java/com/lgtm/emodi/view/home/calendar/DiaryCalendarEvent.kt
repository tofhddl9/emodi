package com.lgtm.emodi.view.home.calendar

sealed class DiaryCalendarEvent {
    data class ScrollMonth(
        val year: Int,
        val month: Int,
    ) : DiaryCalendarEvent()

    data class ClickItem(
        val diaryId: Long,
    ) : DiaryCalendarEvent()
}
