
package com.elize.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elize.news.R
import com.elize.news.model.News
import com.elize.news.ui.activity.extensions.displayError
import com.elize.news.ui.viewmodel.DisplayNewsViewModel
import kotlinx.android.synthetic.main.activity_display_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DisplayNewsActivity : AppCompatActivity() {
    private val newsId: Long by lazy {
        intent.getLongExtra(NEWS_ID_KEY, 0)
    }

    private val displayNewsViewModel: DisplayNewsViewModel by viewModel {
        parametersOf(newsId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_news)
        title = this.getString(R.string.app_name)
        checkNewsIsValid()
        getSelectedNews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.display_news_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.display_news_menu_update -> openUpdateMenu()
            R.id.display_news_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedNews() {
        displayNewsViewModel.currentNews.observe(this, { foundNews ->
            foundNews?.let { currentNews ->
                fillFields(currentNews)
            }
        })
    }

    private fun checkNewsIsValid() {
        if (!displayNewsViewModel.isValidNews()) {
            displayError(this.getString(R.string.news_not_found))
            finish()
        }
    }

    private fun fillFields(news: News) {
        activity_display_news_title.text = news.titulo
        activity_display_news_text.text = news.texto
    }

    private fun remove() {
        displayNewsViewModel.remove()?.observe(this, {
            if (it.error != null) {
                displayError(this.getString(R.string.error_message_remove))
            } else {
                finish()
            }
        })
    }

    private fun openUpdateMenu() {
        val intent = Intent(this, FormNewsActivity::class.java)
        intent.putExtra(NEWS_ID_KEY, newsId)
        startActivity(intent)
    }

}
