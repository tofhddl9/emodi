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
import com.lgtm.emoji_diary.utils.EmojiStore
import com.lgtm.emoji_diary.view.edit.SimpleDate
import com.lgtm.emoji_diary.view.edit.getCurrentSimpleDate
import java.time.LocalDate
import java.time.YearMonth

class EmojiDayBinder(
    private val context: Context,
    private val onItemClicked: ((Long, SimpleDate) -> Unit)? = null,
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
        val diaryInfo = emojiMap[day.asSimpleDate(hourOfDay = 0, minute = 0)]

        container.binding.root.setOnClickListener {
            val currentDate = getCurrentSimpleDate()
            onItemClicked?.invoke(
                diaryInfo?.id ?: -1L,
                day.asSimpleDate(hourOfDay = currentDate.hourOfDay, minute = currentDate.minute)
            )
        }

        val today = LocalDate.now()
        diaryInfo?.also {
            container.textView.isVisible = false
            container.emojiView.isVisible = true

            container.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(context, it.emojiId))
        } ?: run {
            container.textView.isVisible = true
            container.emojiView.isVisible = false

            if (day.date == today) {
                container.textView.setBackgroundResource(R.drawable.bg_selected_day)
            } else {
                container.textView.setBackground(null)
            }
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

private fun CalendarDay.asSimpleDate(
    year: Int = date.year,
    month: Int = date.monthValue,
    dayOfMonth: Int = date.dayOfMonth,
    dayOfWeek: Int = 1,
    hourOfDay: Int = 0,
    minute: Int = 0
) = SimpleDate(year, month, dayOfMonth, dayOfWeek, hourOfDay, minute)