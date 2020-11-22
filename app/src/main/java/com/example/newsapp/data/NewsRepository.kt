package com.example.newsapp.data

import android.util.Log
import com.example.newsapp.data.network.NewsApiService
import com.example.newsapp.domain.Article
import com.example.newsapp.domain.NewsListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewsRepository(private val api: NewsApiService){

    suspend fun getNewsList(): List<Article>? {
        return suspendCoroutine { continuation ->  
            api.getNews().enqueue(object : Callback<NewsListResponse> {
                override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                    if (response.isSuccessful) {
                        val newsListResponse: NewsListResponse? = response.body()
                        val articleList = newsListResponse?.articles
                        continuation.resume(articleList)
                    } else {
                        continuation.resumeWithException(Exception("Server responses with error"))
                        Log.e("newsApp", "NewsRepository : Response in not successful ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                    continuation.resumeWithException(Exception("Server responses with error"))
                    Log.e("newsApp", "NewsRepository : Response in not successful ${t.message}")
                }
            })
        }
    }

}