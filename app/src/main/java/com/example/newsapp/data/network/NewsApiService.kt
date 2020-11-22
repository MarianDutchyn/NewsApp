package com.example.newsapp.data.network

import com.example.newsapp.domain.NewsListResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=ua")
    fun getNews() : Call<NewsListResponse>
}