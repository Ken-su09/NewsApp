package com.suonk.newsapp.ui.fragments.main_pages

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.suonk.newsapp.databinding.FragmentBreakingNewsBinding
import com.suonk.newsapp.models.data.Article
import com.suonk.newsapp.ui.activity.MainActivity
import com.suonk.newsapp.ui.adapters.NewsListAdapter
import com.suonk.newsapp.viewmodels.NewsAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentBreakingNewsBinding? = null

    private lateinit var newsListAdapter: NewsListAdapter

    private val viewModel: NewsAppViewModel by activityViewModels()
    private lateinit var contextActivity: MainActivity

    private var searchQuery: String? = ""
    private var category = "general"

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        contextActivity = activity as MainActivity
        newsListAdapter = NewsListAdapter(contextActivity)
        getDataFromSharedPreferences()
        observeSearchBarTextFromMainFragment()
        observeToolbarSortByFromMainFragment()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.newsRecyclerView?.apply {
            this.adapter = newsListAdapter
            if (searchQuery == "") {
                searchQuery = searchQuery.orEmpty()
            }
            getBreakingNews(searchQuery, category)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(contextActivity)
        }
    }

    private fun observeSearchBarTextFromMainFragment() {
        viewModel.searchBarText.observe(viewLifecycleOwner, { searchBarText ->
            searchQuery = searchBarText
            if (searchQuery == "") {
                searchQuery = searchQuery.orEmpty()
            }
            getBreakingNews(searchQuery, category)
        })
    }

    private fun observeToolbarSortByFromMainFragment() {
//        viewModel.toolbarSortBy.observe(viewLifecycleOwner, { toolbarSortBy ->
//            sortBy = toolbarSortBy
//            getBreakingNews(searchQuery, sortBy)
//        })
    }

    private fun getDataFromSharedPreferences() {
//        sharedPref = contextActivity.getSharedPreferences("news_sort_by", Context.MODE_PRIVATE)
//        sortBy = sharedPref.getString("news_sort_by", "publishedAt")!!
    }

    //endregion

    //region ======================================= GetBreakingNews ========================================

    private fun getBreakingNews(searchQuery: String?, category: String) {
        val countryCode = when (Resources.getSystem().configuration.locale.language) {
            "fr" -> "fr"
            "en" -> "us"
            else -> "fr"
        }
        viewModel.getBreakingNews(searchQuery, countryCode, category)

        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner, { newsResponse ->
            newsListAdapter.submitList(null)
            newsListAdapter.submitList(newsResponse.articles)
        })
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}