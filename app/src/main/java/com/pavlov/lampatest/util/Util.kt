package com.pavlov.lampatest.util

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.opensooq.pluto.PlutoView
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.listeners.OnSlideChangeListener
import com.pavlov.lampatest.model.NewsData


object Util {
    const val BASE_URL = "http://188.40.167.45:3001/"

    fun getDeviceWidth(context: AppCompatActivity): Int {
        val metrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metrics)

        return metrics.widthPixels
    }

    fun getTopNews(mList: List<NewsData>): MutableList<NewsData> {
        val newList = mutableListOf<NewsData>()
        for (item in mList) {
            item.top.let {
                if (it == "1") {
                    newList.add(item)
                }
            }
        }
        return newList
    }

    fun onSliderInit(
        newsSliderIndicator: TabLayout, newsSlider: PlutoView,
        data: List<NewsData>, activity: AppCompatActivity
    ) {
        newsSliderIndicator.removeAllTabs()
        for (i in data.indices) {
            newsSliderIndicator.addTab(newsSliderIndicator.newTab())
        }

        newsSlider.setOnSlideChangeListener(object : OnSlideChangeListener {
            override fun onSlideChange(adapter: PlutoAdapter<*, *>, position: Int) {
                newsSliderIndicator.selectTab(
                    newsSliderIndicator.getTabAt(
                        position
                    )
                )
                newsSlider.stopAutoCycle()
            }
        })

        for (i in 0 until newsSliderIndicator.childCount) {
            val tabs = newsSliderIndicator.getChildAt(i) as ViewGroup
            for (j in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(j)
                tab.setOnClickListener { v: View? ->
                    newsSlider.setCurrentPosition(j, false)
                    newsSlider.stopAutoCycle()
                }
                if (j < tabs.childCount - 1) {
                    val params = tab.layoutParams as LinearLayout.LayoutParams
                    params.marginEnd =
                        8 * (activity
                            .resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
                    tab.layoutParams = params
                }
                newsSliderIndicator.requestLayout()
            }
        }
    }
}