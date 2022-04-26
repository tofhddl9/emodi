package com.lgtm.emoji_diary.view.home.calendar

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentCalendarBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.CalendarUtil

class CalendarFragment : Fragment(R.layout.fragment_calendar), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    private val binding: FragmentCalendarBinding by viewBinding(FragmentCalendarBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setListeners()
    }

    private fun initViews() = with(binding) {
        yearView.text = calendarView.curYear.toString()
        monthView.text = CalendarUtil.monthInEnglish(calendarView.curMonth)
//        val calendar = Calendar().apply {
//            year = 2022
//            month = 2
//            day = 22
//        }
//        calendarView.addSchemeDate(calendar)
//        calendarView.addSchemeDate(mapOf("id" to calendar))
//        db 데이터를 가공해서 이런식으로 캘린더뷰에 연동
    }

    private fun setListeners() {
        binding.calendarView.setOnCalendarSelectListener(this)

        binding.calendarView.setOnYearChangeListener(this)

        // TODO : Animation
        binding.yearView.setOnClickListener {
            val currentYear = binding.calendarView.curYear
            binding.calendarView.showYearSelectLayout(currentYear)
            binding.yearView.text = currentYear.toString()

            binding.monthView.isVisible = false
        }
    }

    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        updateView(calendar)
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        // do nothing
    }

    private fun updateView(calendar: Calendar) = with(binding) {
        yearView.text = calendar.year.toString()
        monthView.text = CalendarUtil.monthInEnglish(calendar.month)

        monthView.isVisible = true
    }

    override fun onYearChange(year: Int) = with(binding) {
        yearView.text = year.toString()
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }

}