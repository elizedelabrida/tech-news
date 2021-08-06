package com.elize.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elize.news.model.News
import com.elize.news.repository.NewsRepository
import com.elize.news.repository.Resource
import com.elize.news.repository.createEmptyResource
import com.elize.news.repository.createFailResource

class DisplayNewsViewModel(
    private val newsRepository: NewsRepository,
    private val newsId: Long
) : ViewModel() {

    val currentNews = newsRepository.getById(newsId)

    fun remove(): LiveData<Resource<Void>> {
        currentNews.value?.let { news ->
            return newsRepository.remove(news)
        }

        val failResource = createFailResource(createEmptyResource(), "Not possible to remove empty news")
        return MutableLiveData(failResource)
    }

    fun isValidNews() = newsId != 0L

    class Factory(private val newsRepository: NewsRepository,
                  private val newsId: Long) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DisplayNewsViewModel(newsRepository, newsId) as T
        }
    }
}