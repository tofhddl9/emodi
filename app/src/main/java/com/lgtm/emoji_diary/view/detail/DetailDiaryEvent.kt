package com.lgtm.emoji_diary.view.detail

sealed class DetailDiaryEvent {
    data class EditDiary(val diaryId: Long) : DetailDiaryEvent()
    data class RemoveDiary(val diaryId: Long) : DetailDiaryEvent()
}