package com.elize.news.retrofit.service

import com.elize.news.model.News
import retrofit2.Call
import retrofit2.http.*

interface NewsService {

    @GET("noticias")
    fun listAll(): Call<List<News>>

    @POST("noticias")
    fun save(@Body news: News): Call<News>

    @PUT("noticias/{id}")
    fun update(@Path("id") id: Long, @Body news: News) : Call<News>

    @DELETE("noticias/{id}")
    fun remove(@Path("id") id: Long): Call<Void>

}
