package com.lgtm.emoji_diary.view.home.timeline

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentTimelineBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.view.home.HomeFragmentResult
import com.lgtm.emoji_diary.view.home.HomeViewModel
import com.lgtm.emoji_diary.view.home.TabInfo
import com.lgtm.emoji_diary.view.home.calendar.CalendarFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    private val binding: FragmentTimelineBinding by viewBinding(FragmentTimelineBinding::bind)

    private val viewModel: TimelineViewModel by viewModels()

//    private val homeViewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTimelineList()

        observeViewModel()
    }

    private fun initTimelineList() {
        val timelineListAdapter = TimelineListAdapter(::onTimelineItemClicked)
        with(binding.timelineList) {
            adapter = timelineListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.uiState.observe(viewLifecycleOwner, { uiState ->
            timelineListAdapter.submitList(uiState.diaryList)
        })
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner, { uiState ->
            uiState.loadErrorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onTimelineItemClicked(diaryId: Long) {
        setFragmentResult(
            HomeFragmentResult.KEY_TIMELINE_ON_CLICK,
            bundleOf(HomeFragmentResult.KEY_DIARY_ID to diaryId)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDiaries()
    }

    companion object {
        val TAB_INFO = TabInfo("타임라인") { newInstance() }

        private fun newInstance() = TimelineFragment()
    }
}