package com.example.newsorgapp.source.news

import com.example.newsorgapp.source.network.ApiClient
import com.example.newsorgapp.utils.Constant
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get()) }
}

class NewsRepository(
    private val api: ApiClient
) {
    suspend fun fetch(
        category: String,
        query: String,
        page: Int
    ) : NewsModel {
        return api.fetchNews(
            apiKey = Constant.API_KEY,
            country = "id",
            category = category,
            query = query,
            page = page,
        )
    }
}