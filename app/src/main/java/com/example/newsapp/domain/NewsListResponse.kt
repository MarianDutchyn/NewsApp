package com.example.newsapp.domain

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
)