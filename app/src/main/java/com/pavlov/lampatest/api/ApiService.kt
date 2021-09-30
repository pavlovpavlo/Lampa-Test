package com.pavlov.lampatest.api

import com.pavlov.lampatest.model.NewsData
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @GET(".")
    fun getNews(@Query("page") page: String): Observable<List<NewsData>>
}