package com.lgtm.emoji_diary.view.edit

sealed class EditDiaryEvent {
    data class TitleChanged(val title: String) : EditDiaryEvent()
    data class ContentChanged(val content: String) : EditDiaryEvent()
    data class DateChanged(val simpleDate: SimpleDate) : EditDiaryEvent()
    data class TimeChanged(val simpleDate: SimpleDate) : EditDiaryEvent()
    data class EmojiChanged(val emojiId: Long) : EditDiaryEvent()

    object EmojiPickerClicked : EditDiaryEvent()
    data class DatePickerClicked(
        val date: SimpleDate = getCurrentSimpleDate()
    ) : EditDiaryEvent()
    data class TimePickerClicked(
        val date: SimpleDate = getCurrentSimpleDate()
    ) : EditDiaryEvent()

    object Save : EditDiaryEvent()
}
