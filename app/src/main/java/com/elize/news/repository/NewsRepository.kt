package com.elize.news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.elize.news.asynctask.BaseAsyncTask
import com.elize.news.database.dao.NewsDAO
import com.elize.news.model.News
import com.elize.news.retrofit.webclient.NewsWebClient

class NewsRepository(
    private val dao: NewsDAO,
    private val webclient: NewsWebClient
) {
    private val mediator = MediatorLiveData<Resource<List<News>>>()

    fun listAll(): LiveData<Resource<List<News>>> {
        addNewsFromDatabaseToMediator()

        val webApiErrors = MutableLiveData<Resource<List<News>>>()
        configureWebApiErrors(webApiErrors)

        searchNewsInApi(webApiErrors)

        return mediator
    }

    private fun addNewsFromDatabaseToMediator() {
        mediator.addSource(searchInternalDatabase()) { foundNews ->
            mediator.value = Resource(data = foundNews)
        }
    }

    private fun configureWebApiErrors(webApiErrors: MutableLiveData<Resource<List<News>>>) {
        mediator.addSource(webApiErrors) { failResource ->
            val currentResource = mediator.value
            val newResource = if (currentResource != null) {
                Resource(data = currentResource.data, error = failResource.error)
            } else {
                failResource
            }
            mediator.value = newResource
        }
    }

    private fun searchNewsInApi(webApiErrors: MutableLiveData<Resource<List<News>>>) {
        searchNewsInApi(whenFail = { error ->
            webApiErrors.value = Resource(data = null, error = error)
        })
    }

    fun save(
        news: News,
    ): LiveData<Resource<Void>> {
        val result = MutableLiveData<Resource<Void>>()
        val emptyResource = createEmptyResource()

        saveInApi(news, whenSuccess = {
            result.value = emptyResource
        }, whenFail = { error ->
            result.value = createFailResource(emptyResource, error)
        })

        return result
    }

    fun update(
        news: News,
    ): LiveData<Resource<Void>> {
        val result = MutableLiveData<Resource<Void>>()
        val emptyResource = createEmptyResource()

        updateInApi(news, whenSuccess = {
            result.value = emptyResource
        }, whenFail = { error ->
            result.value = createFailResource(emptyResource, error)
        })

        return result
    }

    fun remove(
        news: News,
    ): LiveData<Resource<Void>> {
        val result = MutableLiveData<Resource<Void>>()
        val emptyResource = createEmptyResource()

        removeInApi(news, whenSuccess = {
            result.value = emptyResource
        }, whenFail = { error ->
            result.value = createFailResource(emptyResource, error)
        })

        return result
    }

    fun getById(
        newsId: Long,
    ): LiveData<News?> {
        return dao.getById(newsId)
    }

    private fun searchNewsInApi(
        whenFail: (error: String?) -> Unit
    ) {
        return webclient.listAll(
            whenSuccess = { newNews ->
                newNews?.let {
                    saveInternalDatabase(newNews)
                }
            }, whenFail = whenFail
        )
    }

    private fun searchInternalDatabase(): LiveData<List<News>> {
        return dao.listAll()
    }


    private fun saveInApi(
        news: News,
        whenSuccess: () -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        webclient.save(
            news,
            whenSuccess = { news ->
                news?.let { savedNews ->
                    saveInternalDatabase(savedNews, whenSuccess)
                }
            }, whenFail = whenFail
        )
    }

    private fun saveInternalDatabase(
        news: List<News>,
    ) {
        BaseAsyncTask(
            whenExecute = {
                dao.save(news)
            }, whenFinish = {}
        ).execute()
    }

    private fun saveInternalDatabase(
        news: News,
        whenSuccess: () -> Unit
    ) {
        BaseAsyncTask(whenExecute = {
            dao.save(news)
        }) { whenSuccess() }.execute()

    }

    private fun removeInApi(
        news: News,
        whenSuccess: () -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        webclient.remove(
            news.id,
            whenSuccess = {
                removeInternal(news, whenSuccess)
            },
            whenFail = whenFail
        )
    }


    private fun removeInternal(
        news: News,
        whenSuccess: () -> Unit
    ) {
        BaseAsyncTask(whenExecute = {
            dao.remove(news)
        }) {
            whenSuccess()
        }.execute()
    }

    private fun updateInApi(
        news: News,
        whenSuccess: () -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        webclient.update(
            news.id, news,
            whenSuccess = { updatedNews ->
                updatedNews?.let {
                    saveInternalDatabase(updatedNews, whenSuccess)
                }
            }, whenFail = whenFail
        )
    }

}
