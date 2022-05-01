package com.lgtm.emoji_diary

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

fun interface FragmentProvider {
    fun provide(): Fragment
}

class ViewPagerFragmentStateAdapter(
    private val fragments: List<FragmentProvider>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int) = fragments[position].provide()

}