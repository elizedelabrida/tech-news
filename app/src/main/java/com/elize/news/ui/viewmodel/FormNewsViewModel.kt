package com.elize.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elize.news.model.News
import com.elize.news.repository.NewsRepository
import com.elize.news.repository.Resource

class FormNewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getById(newsId: Long) = newsRepository.getById(newsId)

    fun save(news: News) : LiveData<Resource<Void>> {
        if (isUpdateMode(news.id)) {
            return newsRepository.update(news)
        }
        return newsRepository.save(news)
    }

    fun isUpdateMode(newsId: Long) = newsId > 0

    class Factory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FormNewsViewModel(newsRepository) as T
        }
    }
}