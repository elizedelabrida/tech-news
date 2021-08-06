package com.elize.news.di.modules

import androidx.room.Room
import com.elize.news.database.AppDatabase
import com.elize.news.database.dao.NewsDAO
import com.elize.news.repository.NewsRepository
import com.elize.news.retrofit.webclient.NewsWebClient
import com.elize.news.ui.viewmodel.DisplayNewsViewModel
import com.elize.news.ui.viewmodel.FormNewsViewModel
import com.elize.news.ui.viewmodel.ListNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NAME = "news.db"

val appModules = module {
    single<AppDatabase> { Room.databaseBuilder(
        get(),
        AppDatabase::class.java,
        DATABASE_NAME
    ).build() }

    single<NewsDAO> {
        get<AppDatabase>().newsDAO
    }

    single<NewsWebClient>() {
        NewsWebClient()
    }

    single<NewsRepository> {
        NewsRepository(get(), get())
    }

    viewModel<ListNewsViewModel> {
        ListNewsViewModel(get())
    }

    viewModel<DisplayNewsViewModel> { (id: Long) ->
        DisplayNewsViewModel(get(), id)
    }

    viewModel<FormNewsViewModel> {
        FormNewsViewModel(get())
    }
}