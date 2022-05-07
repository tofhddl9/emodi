package com.lgtm.emodi.view.edit

import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.data.getCurrentSimpleDate

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

    object ImagePickerClicked : EditDiaryEvent()
    object Save : EditDiaryEvent()
}
