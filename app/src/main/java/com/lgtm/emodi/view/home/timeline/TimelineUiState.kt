package com.lgtm.emodi.view.home.timeline

import com.lgtm.emodi.data.Diary

data class TimelineUiState(
    val diaryList: List<Diary> = listOf(),
    val loadErrorMessage: String? = null,
)