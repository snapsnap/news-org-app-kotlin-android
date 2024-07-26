package com.example.newsorgapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsorgapp.source.network.networkModule
import com.example.newsorgapp.source.news.repositoryModule
import com.example.newsorgapp.source.persistence.databaseModule
import com.example.newsorgapp.ui.bookmark.bookmarkModule
import com.example.newsorgapp.ui.bookmark.bookmarkViewModel
import com.example.newsorgapp.ui.detail.detailModule
import com.example.newsorgapp.ui.detail.detailViewModel
import com.example.newsorgapp.ui.home.homeModule
import com.example.newsorgapp.ui.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.e("run base application")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(
                networkModule,
                repositoryModule,
                homeViewModel,
                homeModule,
                bookmarkViewModel,
                bookmarkModule,
                databaseModule,
                detailModule,
                detailViewModel
            )
        }
    }
}