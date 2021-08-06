package com.elize.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elize.news.model.News
import com.elize.news.repository.NewsRepository
import com.elize.news.repository.Resource

class ListNewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    fun listAll() : LiveData<Resource<List<News>>> {
        return repository.listAll()
    }

    class Factory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListNewsViewModel(newsRepository) as T
        }
    }

}