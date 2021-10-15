package com.suonk.newsapp.repositories

import com.suonk.newsapp.api.NewsApiService
import javax.inject.Inject

class NewsAppRepository @Inject constructor(
    private var api: NewsApiService
) {

    suspend fun getAllNews(searchQuery: String?, language: String, sortBy: String) =
        api.getAllNews(searchQuery, language, sortBy)

    suspend fun getBreakingNews(searchQuery: String?, countryCode: String, category: String) =
        api.getBreakingNews(searchQuery, countryCode, category)
}