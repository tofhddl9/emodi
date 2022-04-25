package com.lgtm.emoji_diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.lgtm.emoji_diary.databinding.FragmentMainBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.CalendarUtil

class MainFragment: Fragment(R.layout.fragment_main), CalendarView.OnCalendarSelectListener {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setOnCalendarSelectListener(this)

        initViews()
    }

    private fun initViews() = with(binding) {
        yearView.text = calendarView.curYear.toString()
        monthView.text = CalendarUtil.monthInEnglish(calendarView.curMonth)
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        // do nothing
    }

    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        updateView(calendar)
    }

    private fun updateView(calendar: Calendar) = with(binding) {
        yearView.text = calendar.year.toString()
       monthView.text = CalendarUtil.monthInEnglish(calendar.month)
    }

}