package com.pavlov.lampatest.ui.main.adapters

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder
import com.pavlov.lampatest.R
import com.pavlov.lampatest.model.NewsData

class TopNewsAdapter(
    mList: List<NewsData>
) :
    PlutoAdapter<NewsData, TopNewsAdapter.ViewHolderNews>(mList as MutableList<NewsData>) {

    class ViewHolderNews(parent: ViewGroup?, itemLayoutId: Int) :
        PlutoViewHolder<NewsData>(parent!!, itemLayoutId) {
        private val newsImage: ImageView = itemView.findViewById(R.id.news_image)
        private val newsLink: TextView = itemView.findViewById(R.id.news_link)
        private val newsName: TextView = itemView.findViewById(R.id.news_title)
        private val newsDate: TextView = itemView.findViewById(R.id.news_date)

        override fun set(item: NewsData, position: Int) {
            newsDate.text = "- ${item.time}"
            newsName.text = item.title
            if (item.url.isNullOrEmpty())
                newsLink.text = item.clickUrl
            else
                newsLink.text = item.url

            Glide.with(itemView.context)
                .load(item.img)
                .into(newsImage)
        }
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {
        return ViewHolderNews(parent, R.layout.item_top_news)
    }
}
    