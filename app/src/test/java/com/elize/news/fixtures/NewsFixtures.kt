package com.elize.news.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elize.news.model.News


val news = News(id = 0,
        titulo = "Test title",
        texto = "Test description")

val newsList: List<News> = listOf(news)

val newsListLiveData : LiveData<List<News>> = MutableLiveData(newsList)
