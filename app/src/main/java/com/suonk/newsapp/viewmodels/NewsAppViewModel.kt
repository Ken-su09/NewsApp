package com.suonk.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suonk.newsapp.models.data.NewsResponse
import com.suonk.newsapp.repositories.NewsAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsAppViewModel @Inject constructor(private val repository: NewsAppRepository) :
    ViewModel() {

    //region =========================================== Get News ===========================================

    var allNewsLiveData = MutableLiveData<NewsResponse>()
    var breakingNewsLiveData = MutableLiveData<NewsResponse>()

    fun getAllNews(searchQuery: String?, language: String, sortBy: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllNews(searchQuery, language, sortBy)
            if (response.isSuccessful) {
                allNewsLiveData.postValue(response.body())
            }
        }

    fun getBreakingNews(searchQuery: String?, countryCode: String, category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBreakingNews(searchQuery, countryCode, category)
            Log.i("getNews", "getBreakingNews : ${response.isSuccessful}")
            Log.i("getNews", "getBreakingNews : $response")
            Log.i("getNews", "getBreakingNews : $searchQuery")
            if (response.isSuccessful) {
                Log.i("getNews", "getBreakingNews : ${response.body()}")
                breakingNewsLiveData.postValue(response.body())
            }
        }

    //endregion

    //region ==================================== Data Between Fragments ====================================

    val searchBarText = MutableLiveData<String>()
    fun setSearchBarText(text: String) {
        searchBarText.value = text
    }

    val toolbarSortBy = MutableLiveData<String>()
    fun setToolbarSortBy(text: String) {
        toolbarSortBy.value = text
    }

    //endregion
}