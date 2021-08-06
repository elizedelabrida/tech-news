package com.elize.news.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.elize.news.R
import com.elize.news.model.News
import com.elize.news.ui.activity.extensions.displayError
import com.elize.news.ui.recyclerview.adapter.ListNewsAdapter
import com.elize.news.ui.viewmodel.ListNewsViewModel
import kotlinx.android.synthetic.main.activity_list_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListNewsActivity : AppCompatActivity() {
    private val adapter by lazy {
        ListNewsAdapter(context = this)
    }

    private val listNewsViewModel: ListNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)
        title = this.getString(R.string.app_name)
        configureRecyclerView()
        configureFabAddNews()
    }

    override fun onResume() {
        super.onResume()
        getNews()
    }

    private fun configureRecyclerView() {
        val divisor = DividerItemDecoration(this, VERTICAL)
        activity_list_news_recyclerview.addItemDecoration(divisor)
        activity_list_news_recyclerview.adapter = adapter
        configureAdapter()
    }

    private fun configureFabAddNews() {
        activity_list_news_fab_save_news.setOnClickListener {
            openFormCreateMode()
        }
    }

    private fun configureAdapter() {
        adapter.whenItemClicked = this::openNewsVisualizer
    }

    private fun getNews() {
        listNewsViewModel.listAll().observe(this, { resource ->
            if (resource.data != null) {
                adapter.update(resource.data)
            }
            if (resource.error != null) {
                displayError(this.getString(R.string.error_message_load))
            }
        })
    }

    private fun openFormCreateMode() {
        val intent = Intent(this, FormNewsActivity::class.java)
        startActivity(intent)
    }

    private fun openNewsVisualizer(it: News) {
        val intent = Intent(this, DisplayNewsActivity::class.java)
        intent.putExtra(NEWS_ID_KEY, it.id)
        startActivity(intent)
    }

}
