package com.suonk.newsapp.ui.fragments.main_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.suonk.newsapp.R
import com.suonk.newsapp.databinding.FragmentMainBinding
import com.suonk.newsapp.ui.activity.MainActivity
import com.suonk.newsapp.ui.adapters.ViewPagerAdapter

class MainFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentMainBinding? = null

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initializeUI()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding?.viewPager?.adapter = ViewPagerAdapter(activity as MainActivity)

        TabLayoutMediator(
            binding?.tabLayout!!,
            binding?.viewPager!!
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All News"
                }
                1 -> {
                    tab.text = "Breaking News"
                }
            }
        }.attach()
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}