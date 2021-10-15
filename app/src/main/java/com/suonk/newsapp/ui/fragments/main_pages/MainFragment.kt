package com.suonk.newsapp.ui.fragments.main_pages

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.suonk.newsapp.R
import com.suonk.newsapp.databinding.FragmentMainBinding
import com.suonk.newsapp.ui.activity.MainActivity
import com.suonk.newsapp.ui.adapters.ViewPagerAdapter
import com.suonk.newsapp.viewmodels.NewsAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentMainBinding? = null
    private val viewModel: NewsAppViewModel by activityViewModels()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var contextActivity: MainActivity

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        contextActivity = activity as MainActivity
        initToolbar()
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

    private fun initToolbar() {
        searchBarTextListener()

        binding?.toolbar?.apply {
            inflateMenu(R.menu.news_toolbar_menu)
            overflowIcon = ResourcesCompat.getDrawable(
                (activity as MainActivity).resources,
                R.drawable.ic_sort,
                null
            )

            getDataFromSharedPreferences()

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.publishedAt -> {
                        Log.i("news_sort_by", "publishedAt")
                        viewModel.setToolbarSortBy("publishedAt")
                        val edit = sharedPref.edit()
                        edit.putString("news_sort_by", "publishedAt")
                        edit.apply()
                        menuItem.isChecked = true
                        true
                    }
                    R.id.relevancy -> {
                        Log.i("news_sort_by", "relevancy")
                        viewModel.setToolbarSortBy("relevancy")
                        val edit = sharedPref.edit()
                        edit.putString("news_sort_by", "relevancy")
                        edit.apply()
                        menuItem.isChecked = true
                        true
                    }
                    R.id.popularity -> {
                        Log.i("news_sort_by", "popularity")
                        viewModel.setToolbarSortBy("popularity")
                        val edit = sharedPref.edit()
                        edit.putString("news_sort_by", "popularity")
                        edit.apply()
                        menuItem.isChecked = true
                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(menuItem)
                        true
                    }
                }
            }
        }
    }

    private fun searchBarTextListener() {
        binding?.searchNewsEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text?.toString() != null) {
                    viewModel.setSearchBarText(text.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun getDataFromSharedPreferences() {
        sharedPref = contextActivity.getSharedPreferences("news_sort_by", Context.MODE_PRIVATE)

        when (sharedPref.getString("news_sort_by", "publishedAt")) {
            "publishedAt" -> {
                binding?.toolbar?.menu?.getItem(0)?.isChecked = true
            }
            "relevancy" -> {
                binding?.toolbar?.menu?.getItem(1)?.isChecked = true
            }
            "popularity" -> {
                binding?.toolbar?.menu?.getItem(2)?.isChecked = true
            }
        }
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}