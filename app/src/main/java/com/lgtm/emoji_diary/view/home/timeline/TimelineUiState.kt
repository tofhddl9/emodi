package com.lgtm.emoji_diary.view.home.timeline

import com.lgtm.emoji_diary.data.Diary

data class TimelineUiState(
    val diaryList: List<Diary> = listOf(),
    val loadErrorMessage: String? = null,
)