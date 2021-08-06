package com.elize.news.retrofit.webclient

import com.elize.news.model.News
import com.elize.news.retrofit.AppRetrofit
import com.elize.news.retrofit.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUEST_FAILED = "Request failed"

class NewsWebClient(
    private val service: NewsService = AppRetrofit().newsService
) {

    private fun <T> executeRequest(
        call: Call<T>,
        whenSuccess: (newNews: T?) -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    whenSuccess(response.body())
                } else {
                    whenFail(REQUEST_FAILED)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                whenFail(t.message)
            }
        })
    }

    fun listAll(
        whenSuccess: (newNews: List<News>?) -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        executeRequest(
            service.listAll(),
            whenSuccess,
            whenFail
        )
    }

    fun save(
        news: News,
        whenSuccess: (newNews: News?) -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        executeRequest(service.save(news), whenSuccess, whenFail)
    }

    fun update(
        id: Long,
        news: News,
        whenSuccess: (newNews: News?) -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        executeRequest(service.update(id, news), whenSuccess, whenFail)
    }

    fun remove(
        id: Long,
        whenSuccess: (newNews: Void?) -> Unit,
        whenFail: (error: String?) -> Unit
    ) {
        executeRequest(service.remove(id), whenSuccess, whenFail)
    }

}
