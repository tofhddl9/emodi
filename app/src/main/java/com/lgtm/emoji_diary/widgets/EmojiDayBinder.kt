package com.lgtm.emoji_diary.widgets

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.SimpleDate
import com.lgtm.emoji_diary.data.getCurrentSimpleDate
import com.lgtm.emoji_diary.utils.EmojiStore
import java.time.LocalDate

class EmojiDayBinder(
    private val context: Context,
    private val onItemClicked: ((Long, SimpleDate) -> Unit)? = null,
): DayBinder<EmojiDayViewContainer> {

    private val emojiMap = mutableMapOf<SimpleDate, Diary>()

    fun setEmojiMap(map: Map<SimpleDate, Diary>) {
        emojiMap.clear()
        emojiMap.putAll(map)
    }

    override fun create(view: View) = EmojiDayViewContainer(view)

    override fun bind(container: EmojiDayViewContainer, day: CalendarDay) {
        val diaryInfo = emojiMap[day.asSimpleDate(hourOfDay = 0, minute = 0)]

        container.binding.root.setOnClickListener {
            val currentDate = getCurrentSimpleDate()
            onItemClicked?.invoke(
                diaryInfo?.id ?: -1L,
                day.asSimpleDate(hourOfDay = currentDate.hourOfDay, minute = currentDate.minute)
            )
        }

        diaryInfo?.also {
            container.textView.isVisible = false
            container.emojiView.isVisible = true

            container.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(context, it.emojiId))
        } ?: run {
            container.textView.isVisible = true
            container.emojiView.isVisible = false

            container.textView.setTextColor(day.textColor())
            container.textView.text = day.day.toString()
        }

        val today = LocalDate.now()
        if (day.date == today) {
            container.binding.root.setBackgroundResource(R.drawable.bg_selected_day)
        } else {
            container.binding.root.background = null
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

private fun CalendarDay.asSimpleDate(
    year: Int = date.year,
    month: Int = date.monthValue,
    dayOfMonth: Int = date.dayOfMonth,
    dayOfWeek: Int = 1,
    hourOfDay: Int = 0,
    minute: Int = 0
) = SimpleDate(year, month, dayOfMonth, dayOfWeek, hourOfDay, minute)