package com.example.newsapp

import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.network.NewsApiService
import com.example.newsapp.data.network.RequestInterceptor
import com.example.newsapp.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { MainViewModel()}
}

val repositoryModule = module {
    single { NewsRepository(get()) }
}

val networkModule = module {
    factory { RequestInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideNewsApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(requestInterceptor)
        .build()
}

fun provideNewsApi(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)