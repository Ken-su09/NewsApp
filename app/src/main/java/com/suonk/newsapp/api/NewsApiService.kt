package com.suonk.newsapp.api

import com.suonk.newsapp.models.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "e8071f9f002745c5bb2ee6dd9c00d203"
    }

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q")
        searchQuery: String?,
        @Query("language")
        language: String,
        @Query("sortBy")
        sortBy: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("q")
        searchQuery: String?,
        @Query("country")
        countryCode: String,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}