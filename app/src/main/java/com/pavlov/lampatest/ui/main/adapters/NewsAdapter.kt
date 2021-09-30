package com.pavlov.lampatest.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.opensooq.pluto.PlutoView
import com.pavlov.lampatest.R
import com.pavlov.lampatest.model.NewsData
import com.pavlov.lampatest.util.Util

class NewsAdapter(
    private var mList: List<NewsData>,
    private val heightImage: Int,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && Util.getTopNews(mList).isNotEmpty())
            return 1

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)

        return if (viewType == 1) {
            val vGroupImage =
                view.inflate(R.layout.item_top_news_container, parent, false) as ViewGroup
            ViewHolderTopNews(vGroupImage)
        } else {
            val vGroupImage =
                view.inflate(R.layout.item_news, parent, false) as ViewGroup
            ViewHolderNews(vGroupImage)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderNews) {
            var newsPosition = position
            if (Util.getTopNews(mList).isNotEmpty())
                newsPosition -= 1
            setDataNews(holder, newsPosition)
        } else
            setDataTopNews(holder as ViewHolderTopNews, position)
    }

    private fun setDataNews(holder: ViewHolderNews, position: Int) {
        val data = mList[position]

        holder.newsImage.layoutParams.height = heightImage
        holder.newsDate.text = "- ${data.time}"
        holder.newsName.text = data.title
        if (data.url.isNullOrEmpty())
            holder.newsLink.text = data.clickUrl
        else
            holder.newsLink.text = data.url

        if (data.img.isNullOrEmpty())
            holder.newsImage.visibility = View.GONE
        else {
            holder.newsImage.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(data.img)
                .into(holder.newsImage)
        }
    }

    private fun setDataTopNews(holder: ViewHolderTopNews, position: Int) {
        val data = Util.getTopNews(mList)

        val adapter = TopNewsAdapter(data)
        holder.newsSlider.create(adapter, 0, activity.lifecycle)
        holder.newsSlider.stopAutoCycle()
        if (data.isNotEmpty()) {
            holder.newsSlider.setCurrentPosition(0, false)
            holder.itemView.visibility = View.VISIBLE

            Util.onSliderInit(holder.newsSliderIndicator, holder.newsSlider, data, activity)
        } else
            holder.itemView.visibility = View.GONE
    }

    fun setNews(list: List<NewsData>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mList.isEmpty() || Util.getTopNews(mList).isEmpty())
            mList.size
        else
            mList.size + 1
    }

    class ViewHolderNews(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)
        val newsLink: TextView = itemView.findViewById(R.id.news_link)
        val newsName: TextView = itemView.findViewById(R.id.news_title)
        val newsDate: TextView = itemView.findViewById(R.id.news_date)
    }

    class ViewHolderTopNews(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val newsSlider: PlutoView = itemView.findViewById(R.id.slider_view)
        val newsSliderIndicator: TabLayout = itemView.findViewById(R.id.indicator)
    }
}