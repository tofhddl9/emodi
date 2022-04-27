package com.lgtm.emoji_diary.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.ViewPagerFragmentStateAdapter
import com.lgtm.emoji_diary.databinding.FragmentHomeBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import com.lgtm.emoji_diary.utils.CalendarUtil
import com.lgtm.emoji_diary.view.home.calendar.CalendarFragment
import com.lgtm.emoji_diary.view.home.timeline.TimelineFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding : FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        setupNavigation()
    }

    private fun setupViewPager() {
        ViewPagerFragmentStateAdapter(
            FRAGMENT_LIST,
            requireActivity().supportFragmentManager,
            lifecycle
        ).also {
            binding.viewPager.adapter = it
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = TAB_NAME[position]
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.viewPager.isUserInputEnabled = FRAGMENT_LIST[position] !is CalendarFragment
            }
        })
    }

    private fun setupNavigation() {
        val navController = findNavController()
        binding.fab.setOnClickListener {
            val lastSelectedDate = viewModel.selectedDate.value ?: CalendarUtil.getCurrentSimpleDate()
            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(lastSelectedDate)
            navController.navigate(action)
        }

    }

    companion object {
        val FRAGMENT_LIST = arrayListOf(
            CalendarFragment.newInstance(),
            TimelineFragment.newInstance(),
        )

        val TAB_NAME = arrayListOf(
            "캘린더",
            "타임라인",
        )
    }
}