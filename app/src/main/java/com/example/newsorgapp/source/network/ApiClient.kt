package com.example.newsorgapp.source.network

import com.example.newsorgapp.source.news.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("apiKey") apiKey : String,
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("q") query : String,
        @Query("page") page : Int,
    ) : NewsModel

    @GET("top-headlines")
    suspend fun fetchNewsBBC(
        @Query("apiKey") apiKey : String,
        @Query("sources") country : String,
    ) : NewsModel
}