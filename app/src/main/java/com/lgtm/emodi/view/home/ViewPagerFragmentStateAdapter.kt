package com.lgtm.emodi.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

fun interface FragmentProvider {
    fun provide(): Fragment
}

class ViewPagerFragmentStateAdapter(
    private val fragments: List<FragmentProvider>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    val fragmentMap: HashMap<Int, Fragment> = hashMapOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position].provide()
        fragmentMap[position] = fragment

        return fragment
    }

}