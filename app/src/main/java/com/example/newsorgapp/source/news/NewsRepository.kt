package com.example.newsorgapp.source.news

import com.example.newsorgapp.source.network.ApiClient
import com.example.newsorgapp.utils.Constant
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository( get(), get() ) }
}

class NewsRepository(
    private val api: ApiClient,
    val db: NewsDao
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

    suspend fun find(articleModel: ArticleModel) = db.find(articleModel.publishedAt)

    suspend fun save(articleModel: ArticleModel) {
        db.save(articleModel)
    }

    suspend fun remove(articleModel: ArticleModel) {
        db.remove(articleModel)
    }
}