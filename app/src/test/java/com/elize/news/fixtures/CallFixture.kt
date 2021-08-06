package com.elize.news.fixtures

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallFixture<T>(
    private val response: Response<T>
) : Call<T> {

    override fun enqueue(callback: Callback<T>?) {}

    override fun isExecuted(): Boolean = false

    override fun clone(): Call<T> = this

    override fun isCanceled(): Boolean = false

    override fun cancel() {}

    override fun request(): Request? = null

    override fun timeout(): Timeout = Timeout()

    override fun execute(): Response<T> = response
}