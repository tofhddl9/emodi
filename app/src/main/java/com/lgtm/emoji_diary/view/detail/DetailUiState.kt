package com.lgtm.emoji_diary.view.detail

data class DetailUiState(
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val time: String = "",
    val emojiId: Long = 0,
    val loadErrorMessage: String = "",
)
