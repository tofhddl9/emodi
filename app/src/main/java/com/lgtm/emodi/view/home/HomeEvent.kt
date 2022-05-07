package com.lgtm.emodi.view.home

import com.lgtm.emodi.data.SimpleDate

sealed class HomeEvent {
    data class ClickCalendarDay(
        val diaryId: Long,
        val date: SimpleDate,
    ) : HomeEvent()

    data class ClickTimelineItem(
        val diaryId: Long,
    ) : HomeEvent()

}