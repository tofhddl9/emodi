package com.lgtm.emoji_diary.view.home.timeline

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.databinding.FragmentCalendarBinding
import com.lgtm.emoji_diary.databinding.FragmentTimelineBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.view.home.HomeViewModel

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    private val binding: FragmentTimelineBinding by viewBinding(FragmentTimelineBinding::bind)

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTimelineList()

        observeViewModel()
    }

    private fun initTimelineList() {
        val timelineListAdapter = TimelineListAdapter()
        with(binding.timelineList) {
            adapter = timelineListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {

    }

    companion object {
        fun newInstance() = TimelineFragment()
    }
}