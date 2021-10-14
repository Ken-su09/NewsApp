package com.suonk.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suonk.newsapp.models.data.NewsResponse
import com.suonk.newsapp.repositories.NewsAppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsAppViewModel @Inject constructor(private val repository: NewsAppRepository) :
    ViewModel() {

    var breakingNewsLiveData = MutableLiveData<NewsResponse>()
    var allNewsLiveData = MutableLiveData<NewsResponse>()

    fun getBreakingNews(searchQuery: String, countryCode: String, category: String) =
        viewModelScope.launch {
            val response = repository.getBreakingNews(searchQuery, countryCode, category)
            if (response.isSuccessful) {
                breakingNewsLiveData.postValue(response.body())
            }
        }

    fun getAllNews(searchQuery: String, language: String, sortBy: String) = viewModelScope.launch {
        val response = repository.getBreakingNews(searchQuery, language, sortBy)
        if (response.isSuccessful) {
            allNewsLiveData.postValue(response.body())
        }
    }
}