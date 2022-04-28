package com.lgtm.emoji_diary.view.edit

import com.lgtm.emoji_diary.data.Diary

data class EditUiState(
    val title: String = "",
    val titleErrorMessage: String? = null,
    val content: String = "",
    val contentErrorMessage: String? = null,
    val date: String = "",
    val time: String = "",
    val emojiId: Long = 0,
)

fun EditUiState.mapToDiary(): Diary = Diary(
    title = title,
    content = content,
    date = parseToSimpleDate(date, time).asTimeInMillis(),
    emojiId = emojiId,
)

private fun parseToSimpleDate(date: String, time: String): SimpleDate {
    val dateToSimpleDate = makeDateFormatToSimpleDate(date)
    val timeToSimpleDate = makeTimeFormatToSimpleDate(time)

    return SimpleDate(
        dateToSimpleDate.year,
        dateToSimpleDate.month,
        dateToSimpleDate.day,
        timeToSimpleDate.hourOfDay,
        timeToSimpleDate.minute
    )
}
