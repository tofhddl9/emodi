package com.lgtm.emoji_diary.view.home.calendar

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.MonthScrollListener
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentCalendarBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.CalendarUtil
import com.lgtm.emoji_diary.view.edit.SimpleDate
import com.lgtm.emoji_diary.view.home.HomeFragmentResult
import com.lgtm.emoji_diary.view.home.TabInfo
import com.lgtm.emoji_diary.widgets.EmojiDayBinder
import dagger.hilt.android.AndroidEntryPoint
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@AndroidEntryPoint
class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private val binding: FragmentCalendarBinding by viewBinding(FragmentCalendarBinding::bind)

    private val viewModel: CalendarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setClickListeners()

        observeViewModel()
    }

    private fun initViews() {
        initCalendarView()
    }

    private fun initCalendarView() = with(binding) {
        calendarView.dayBinder = EmojiDayBinder(requireContext(), ::onItemClicked)

        calendarView.monthScrollListener = object: MonthScrollListener {
            override fun invoke(date: CalendarMonth) {
                viewModel.onEvent(DiaryCalendarEvent.ScrollMonth(date.year, date.month))
            }

        }

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(240) // past 20 year
        val lastMonth = currentMonth.plusMonths(12) // future 1 year
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        yearView.text = currentMonth.year.toString()
        monthView.text = CalendarUtil.monthInEnglish(currentMonth.monthValue)

        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
    }

    private fun setClickListeners() {
        // listen moveToToday()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.yearView.text = uiState.year.toString()
            binding.monthView.text = CalendarUtil.monthInEnglish(uiState.month)

            (binding.calendarView.dayBinder as? EmojiDayBinder)?.setEmojiMap(uiState.calendarEmojiMap)
            binding.calendarView.updateMonthConfigurationAsync()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDiaries()
    }

    private fun onItemClicked(diaryId: Long, date: SimpleDate) {
        setFragmentResult(
            HomeFragmentResult.KEY_CALENDAR_ON_CLICK,
            bundleOf(HomeFragmentResult.KEY_DIARY_ID to diaryId,
                HomeFragmentResult.KEY_SIMPLE_DATE to date)
        )
    }

    // TODO : add today button for convenient
    private fun moveToToday() {
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(240) // past 20 year
        val lastMonth = currentMonth.plusMonths(12) // future 1 year
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        // viewModel.event()

//        binding.yearView.text = currentMonth.year.toString()
//        binding.monthView.text = CalendarUtil.monthInEnglish(currentMonth.monthValue)
//
//        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
//        binding.calendarView.scrollToMonth(currentMonth)
    }

    companion object {
        val TAB_INFO = TabInfo("캘린더") { newInstance() }

        private fun newInstance() = CalendarFragment()
    }

}

//class CalendarFragment : Fragment(R.layout.fragment_calendar), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {
//
//    private val binding: FragmentCalendarBinding by viewBinding(FragmentCalendarBinding::bind)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initViews()
//
//        setListeners()
//    }
//
//    private fun initViews() = with(binding) {
//        yearView.text = calendarView.curYear.toString()
//        monthView.text = CalendarUtil.monthInEnglish(calendarView.curMonth)
//        val calendar = Calendar().apply {
//            year = 2022
//            month = 5
//            day = 28
//        }
//        //calendarView.addSchemeDate(mapOf("id" to calendar))
//        //db 데이터를 가공해서 이런식으로 캘린더뷰에 연동
//    }
//
//    private fun setListeners() {
//        binding.calendarView.setOnCalendarSelectListener(this)
//
//        binding.calendarView.setOnYearChangeListener(this)
//
//        // TODO : Animation
//        binding.yearView.setOnClickListener {
//            val currentYear = binding.calendarView.curYear
//            binding.calendarView.showYearSelectLayout(currentYear)
//            binding.yearView.text = currentYear.toString()
//
//            binding.monthView.isVisible = false
//        }
//    }
//
//    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
//        updateView(calendar)
//    }
//
//    override fun onCalendarOutOfRange(calendar: Calendar?) {
//        // do nothing
//    }
//
//    private fun updateView(calendar: Calendar) = with(binding) {
//        yearView.text = calendar.year.toString()
//        monthView.text = CalendarUtil.monthInEnglish(calendar.month)
//
//        monthView.isVisible = true
//    }
//
//    override fun onYearChange(year: Int) = with(binding) {
//        yearView.text = year.toString()
//    }
//
//    companion object {
//        val TAB_INFO = TabInfo("캘린더") { newInstance() }
//
//        private fun newInstance() = CalendarFragment()
//    }
//
//}