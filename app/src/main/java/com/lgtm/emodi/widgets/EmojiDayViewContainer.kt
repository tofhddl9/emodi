package com.lgtm.emodi.widgets

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.lgtm.emodi.databinding.CalendarDayLayoutBinding

class EmojiDayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)

    val textView = binding.calendarDayText
    val emojiView = binding.emojiView
}