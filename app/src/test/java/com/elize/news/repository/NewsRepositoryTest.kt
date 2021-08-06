package com.elize.news.repository

import com.elize.news.database.dao.NewsDAO
import com.elize.news.fixtures.newsListLiveData
import com.elize.news.retrofit.webclient.NewsWebClient
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class NewsRepositoryTest {

    private val newsDatabase = mockk<NewsDAO>()
    private val webApiClient = mockk<NewsWebClient>()
    private val newsRepository = NewsRepository(newsDatabase, webApiClient)

    @Test
    fun `when list all and database returns news and web api returns empty, result should be null`() {
        every { newsDatabase.listAll() } returns newsListLiveData
        every { webApiClient.listAll(any(), any()) } returns Unit

        val result = newsRepository.listAll()

        assertThat(result.value?.data, equalTo(null))
    }


}