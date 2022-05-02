package com.lgtm.emoji_diary.view.edit

import com.lgtm.emoji_diary.data.Diary

/**
 *  using var because of 2-way binding
 *  How to handle EditText in MVVM ...?
 */
data class EditUiState(
    val diaryId: Long = -1L,
    var title: String = "",
    val titleErrorMessage: String? = null,
    var content: String = "",
    val contentErrorMessage: String? = null,
    val date: String = "",
    val time: String = "",
    val emojiId: Long = 0,
)

fun EditUiState.mapToDiary(id: Long): Diary = Diary(
    id = id.coerceAtLeast(0L),
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
