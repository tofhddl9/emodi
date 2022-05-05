package com.lgtm.emoji_diary.view.home

import com.lgtm.emoji_diary.view.edit.SimpleDate

sealed class HomeEvent {
    data class ClickCalendarDay(
        val diaryId: Long,
        val date: SimpleDate,
    ) : HomeEvent()

    data class ClickTimelineItem(
        val diaryId: Long,
    ) : HomeEvent()

}