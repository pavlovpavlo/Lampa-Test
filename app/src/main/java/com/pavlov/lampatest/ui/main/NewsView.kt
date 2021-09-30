package com.pavlov.lampatest.ui.main

import com.pavlov.lampatest.model.NewsData

interface NewsView {
    fun onRequestComplete(data: List<NewsData>)
}