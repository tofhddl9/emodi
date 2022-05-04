package com.lgtm.emoji_diary.widgets

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.utils.EmojiStore
import com.lgtm.emoji_diary.view.edit.SimpleDate

class EmojiDayBinder(
    private val context: Context,
): DayBinder<EmojiDayViewContainer> {

    private val emojiMap = mutableMapOf<SimpleDate, Diary>()

    // 일기는 하루에 하나만 쓴다. 같은 key를 가질 수 있겠는데? ...
    // Edit에서 저장할때, year-month-day를 보고 갱신해야하나?
    // Calendar에서 Edit 열때는 무조건 Date이 있으니까 Date 피커를 숨긴다?
    //
    fun setEmojiMap(map: Map<SimpleDate, Diary>) {
        emojiMap.clear()
        emojiMap.putAll(map)
    }

    override fun create(view: View) = EmojiDayViewContainer(view)

    override fun bind(container: EmojiDayViewContainer, day: CalendarDay) {
        val emojiId = emojiMap[day.asSimpleDate()]

        emojiId?.also {
            container.textView.isVisible = false
            container.emojiView.isVisible = true

            container.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(context, it.emojiId))
        } ?: run {
            container.textView.isVisible = true
            container.emojiView.isVisible = false

            container.textView.setTextColor(day.textColor())
            container.textView.text = day.day.toString()
        }

    }

    @ColorInt
    private fun CalendarDay.textColor(): Int {
        return if (owner == DayOwner.THIS_MONTH) {
            Color.BLACK
        } else {
            Color.GRAY
        }
    }
}

private fun CalendarDay.asSimpleDate() = SimpleDate(date.year, date.monthValue, date.dayOfMonth)