package com.lgtm.emoji_diary.widgets

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.lgtm.emoji_diary.databinding.CalendarDayLayoutBinding

class EmojiDayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)

    val textView = binding.calendarDayText
    val emojiView = binding.emojiView
}