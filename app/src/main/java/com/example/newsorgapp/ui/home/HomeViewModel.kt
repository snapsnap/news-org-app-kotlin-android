package com.example.newsorgapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsorgapp.source.news.CategoryModel
import com.example.newsorgapp.source.news.NewsModel
import com.example.newsorgapp.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.ceil

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    val repository: NewsRepository
) : ViewModel() {
    val title = "Berita"
    val category by lazy { MutableLiveData<String>() }
    val message by lazy { MutableLiveData<String>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadMore by lazy { MutableLiveData<Boolean>() }
    val news by lazy { MutableLiveData<NewsModel>() }

    init {
        category.value = ""
        message.value = null
    }

    var query = ""
    var page = 1
    var total = 1

    fun fetch(){
        if(page > 1) loadMore.value = true else loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetch( category.value!!, query, page )

                news.value = response
                val totalResults: Double = response.totalResults / 20.0
                total = ceil( totalResults ).toInt()
                page++

                loading.value = false
                loadMore.value = false
            } catch (e: Exception){
                message.value = "Terjadi kesalahan"
            }
        }
    }

    val categories = listOf<CategoryModel>(
        CategoryModel("","Berita Utama"),
        CategoryModel("business","Bisnis"),
        CategoryModel("entertainment","Hiburan"),
        CategoryModel("general","Umum"),
        CategoryModel("health","Kesehatan"),
        CategoryModel("science","Sains"),
        CategoryModel("sports","Olahraga"),
        CategoryModel("technology","Teknologi"),
    )
}