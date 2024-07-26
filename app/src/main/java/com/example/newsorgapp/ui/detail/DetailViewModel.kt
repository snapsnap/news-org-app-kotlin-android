package com.example.newsorgapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsorgapp.source.news.ArticleModel
import com.example.newsorgapp.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailViewModel = module {
    factory { DetailViewModel( get() ) }
}

class DetailViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    val isBookmarked by lazy { MutableLiveData<Int>(0) }

    fun find(articleModel: ArticleModel) {
        viewModelScope.launch {
            isBookmarked.value = repository.find(articleModel)
        }
    }

    fun bookmark(articleModel: ArticleModel) {
        viewModelScope.launch {
            if(isBookmarked.value == 0) repository.save(articleModel)
            else repository.remove(articleModel)
        }
    }
}