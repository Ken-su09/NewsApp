package com.suonk.newsapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suonk.newsapp.ui.fragments.main_pages.AllNewsFragment
import com.suonk.newsapp.ui.fragments.main_pages.BreakingNewsFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllNewsFragment()
            }
            1 -> {
                BreakingNewsFragment()
            }
            else -> {
                AllNewsFragment()
            }
        }
    }
}