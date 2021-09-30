package com.pavlov.lampatest.model

import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("click_url")
    val clickUrl: String?,
    @SerializedName("time")
    val time: String,
    @SerializedName("top")
    val top: String,
    @SerializedName("img")
    val img: String?,
    @SerializedName("url")
    val url: String?,
)
