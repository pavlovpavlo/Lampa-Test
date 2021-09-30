package com.pavlov.lampatest.ui.main

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.pavlov.lampatest.R
import com.pavlov.lampatest.base.BaseActivity
import com.pavlov.lampatest.custom.EndlessRecyclerViewScrollListener
import com.pavlov.lampatest.model.NewsData
import com.pavlov.lampatest.ui.main.adapters.NewsAdapter
import com.pavlov.lampatest.util.Util


class MainActivity : BaseActivity(R.layout.activity_main), NewsView {

    lateinit var news: RecyclerView
    lateinit var tabLayout: TabLayout
    lateinit var adapter: NewsAdapter
    var newsList: MutableList<NewsData> = mutableListOf()
    var currentList: MutableList<NewsData> = mutableListOf()
    lateinit var presenter: NewsPresenter
    var currentPage: Int = 1
    private var isEnd = false
    private var filterValue = "strories"
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initPresenter() {
        presenter = NewsPresenter(this)
        presenter.attachView(this)
        sendRequest()
    }

    private fun initViews() {
        news = findViewById(R.id.news)
        tabLayout = findViewById<TabLayout>(R.id.news_table)
        initNewsList()
        initTabs()
        initPresenter()
    }

    private fun initNewsList() {
        // 0.58 коеффициэнт соотношения ширины к высоте по дизайну
        val heightImage = (Util.getDeviceWidth(this) * 0.58f).toInt()
        val layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(newsList, heightImage, this)
        news.layoutManager = layoutManager
        news.adapter = adapter

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isEnd) {
                    currentPage++
                    sendRequest()
                }
            }
        }
        news.addOnScrollListener(scrollListener)
    }

    private fun initTabs() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentList = filter(
                    when (tabLayout.selectedTabPosition) {
                        1 -> "video"
                        2 -> "favourites"
                        else -> "strories"
                    }
                )
                adapter.setNews(
                    currentList
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun sendRequest() {
        presenter.sendResponse(currentPage.toString())
    }

    private fun filter(text: String): MutableList<NewsData> {
        filterValue = text
        val newList = mutableListOf<NewsData>()
        for (item in newsList) {
            item.type.let {
                if (it.contains(text)) {
                    newList.add(item)
                }
            }
        }
        return newList
    }

    override fun onRequestComplete(data: List<NewsData>) {
        newsList.addAll(data.toMutableList())

        if (data.isEmpty())
            isEnd = true

        currentList = filter(filterValue)
        adapter.setNews(currentList)
    }
}