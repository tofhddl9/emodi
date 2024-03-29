package com.lgtm.emodi.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.lgtm.emodi.R
import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.data.getCurrentSimpleDate
import com.lgtm.emodi.databinding.FragmentHomeBinding
import com.lgtm.emodi.delegate.viewBinding
import com.lgtm.emodi.utils.setChildFragmentResultListener
import com.lgtm.emodi.utils.showSnackBar
import com.lgtm.emodi.view.home.calendar.CalendarFragment
import com.lgtm.emodi.view.home.timeline.TimelineFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding : FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private val tabInfoList: List<TabInfo> = listOf(
        CalendarFragment.TAB_INFO,
        TimelineFragment.TAB_INFO,
    )

    private val viewPagerAdapter by lazy { ViewPagerFragmentStateAdapter(
            tabInfoList.map { it.fragmentProvider },
            this
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        setupNavigation()

        setupFragmentResultListener()

        observeViewModel()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabInfoList[position].tabName
        }.attach()

        // TODO : CODE SMELL ... HomeFragment 를 제거하는건 어떨까?
        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toolBar.menu.clear()
                if (position == 0) {
                    binding.toolBar.inflateMenu(R.menu.menu_calendar)
                    binding.toolBar.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.action_today -> {
                                val fragment = (binding.viewPager.adapter as ViewPagerFragmentStateAdapter).fragmentMap[position]
                                (fragment as? CalendarFragment)?.moveToToday()
                                return@setOnMenuItemClickListener true
                            }
                            else -> return@setOnMenuItemClickListener false
                        }
                    }
                } else {
                    binding.toolBar.inflateMenu(R.menu.menu_timeline)
                    binding.toolBar.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.action_search -> {
                                showSnackBar("아직 지원하지 않는 기능입니다 :(")
                                return@setOnMenuItemClickListener true
                            }
                            else -> return@setOnMenuItemClickListener false
                        }
                    }
                }

            }
        })

    }

    private fun setupNavigation() {
        binding.fab.setOnClickListener {
            moveToEditPage(getCurrentSimpleDate())
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.diaryClicked.collect {
                    moveToDetailPage(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emptyDiaryClicked.collect {
                    moveToEditPage(it)
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        setChildFragmentResultListener(HomeFragmentResult.KEY_TIMELINE_ON_CLICK) { _, result ->
            val diaryId = result.getLong(HomeFragmentResult.KEY_DIARY_ID)
            viewModel.onEvent(HomeEvent.ClickTimelineItem(diaryId))
        }

        setChildFragmentResultListener(HomeFragmentResult.KEY_CALENDAR_ON_CLICK) { _, result ->
            val diaryId = result.getLong(HomeFragmentResult.KEY_DIARY_ID)
            val date = result.getParcelable(HomeFragmentResult.KEY_SIMPLE_DATE)
                ?: getCurrentSimpleDate()

            viewModel.onEvent(HomeEvent.ClickCalendarDay(diaryId, date))
        }
    }

    private fun moveToEditPage(simpleDate: SimpleDate) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(simpleDate)
        findNavController().navigate(action)
    }

    private fun moveToDetailPage(diaryId: Long) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(diaryId)
        findNavController().navigate(action)
    }

    // TODO : Need to deep dive :(
    override fun onResume() {
        super.onResume()
        binding.viewPager.adapter = viewPagerAdapter
    }

    override fun onStop() {
        binding.viewPager.adapter = null
        super.onStop()
    }
}

data class TabInfo(
    val tabName: String,
    val fragmentProvider: FragmentProvider
)