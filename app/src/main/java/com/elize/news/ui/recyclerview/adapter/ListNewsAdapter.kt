package com.elize.news.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elize.news.R
import com.elize.news.model.News
import kotlinx.android.synthetic.main.item_news.view.*

class ListNewsAdapter(
    private val context: Context,
    private val news: MutableList<News> = mutableListOf(),
    var whenItemClicked: (news: News) -> Unit = {}
) : RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val createdView = LayoutInflater.from(context)
            .inflate(
                R.layout.item_news,
                parent, false
            )
        return ViewHolder(createdView)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = news[position]
        holder.fulfill(news)
    }

    fun update(news: List<News>) {
        notifyItemRangeRemoved(0, this.news.size)
        this.news.clear()
        this.news.addAll(news)
        notifyItemRangeInserted(0, this.news.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var news: News

        init {
            itemView.setOnClickListener {
                if (::news.isInitialized) {
                    whenItemClicked(news)
                }
            }
        }

        fun fulfill(news: News) {
            this.news = news
            itemView.item_news_title.text = news.titulo
            itemView.item_news_text.text = news.texto
        }

    }

}
