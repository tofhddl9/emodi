package com.lgtm.emodi.view.edit

import android.annotation.SuppressLint
import com.lgtm.emodi.data.Diary
import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.data.asTimeInMillis
import com.lgtm.emodi.data.makeDateFormatToSimpleDate
import com.lgtm.emodi.data.makeTimeFormatToSimpleDate

/**
 *  using var for using 2-way binding
 *  TODO : What is the best practice to handle EditText in MVVM ?
 */
data class EditUiState(
    val diaryId: Long = -1L,
    var title: String = "",
    val titleErrorMessage: String? = null,
    var content: String = "",
    val contentErrorMessage: String? = null,
    val date: String = "",
    val datePickerEnabled: Boolean = true,
    val time: String = "",
    val emojiId: Long = -1L,
    val emojiErrorMessage: String? = null,
)

fun EditUiState.mapToDiary(id: Long): Diary = Diary(
    id = id.coerceAtLeast(0L),
    title = title,
    content = content,
    date = parseToSimpleDate(date, time).asTimeInMillis(),
    emojiId = emojiId,
)

@SuppressLint("Range")
private fun parseToSimpleDate(date: String, time: String): SimpleDate {
    val dateToSimpleDate = makeDateFormatToSimpleDate(date)
    val timeToSimpleDate = makeTimeFormatToSimpleDate(time)

    return SimpleDate(
        year = dateToSimpleDate.year,
        month = dateToSimpleDate.month - 1,
        day = dateToSimpleDate.day,
        hourOfDay = timeToSimpleDate.hourOfDay,
        minute = timeToSimpleDate.minute
    )
}
