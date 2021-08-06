package com.elize.news.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elize.news.R
import com.elize.news.model.News
import com.elize.news.ui.activity.extensions.displayError
import com.elize.news.ui.viewmodel.FormNewsViewModel
import kotlinx.android.synthetic.main.activity_form_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FormNewsActivity : AppCompatActivity() {
    private val newsId: Long by lazy {
        intent.getLongExtra(NEWS_ID_KEY, 0)
    }

    private val formNewsViewModel: FormNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_news)
        title = getViewTitle()
        fillForm()
    }

    private fun getViewTitle(): String {
        if (formNewsViewModel.isUpdateMode(newsId)) {
            return this.getString(R.string.update_news)
        }
        return this.getString(R.string.create_news)
    }

    private fun fillForm() {
        formNewsViewModel.getById(newsId).observe(this, { foundNews ->
            foundNews?.let { news ->
                activity_form_news_title.setText(news.titulo)
                activity_form_news_text.setText(news.texto)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_news_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.form_news_save -> {
                val title = activity_form_news_title.text.toString()
                val text = activity_form_news_text.text.toString()
                save(News(newsId, title, text))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save(news: News) {
        formNewsViewModel.save(news).observe(this, { resource ->
            if (resource.error != null) {
                displayError(this.getString(R.string.error_message_save))
            } else {
                finish()
            }
        })
    }


}
