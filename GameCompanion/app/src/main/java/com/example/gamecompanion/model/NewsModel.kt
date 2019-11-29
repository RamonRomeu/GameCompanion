package com.example.gamecompanion.model

data class NewsList(
    var news: ArrayList<NewsModel>?
)

data class NewsModel (
    val newsId: String?=null,
    val newsTitle: String?=null,
    val newsDescription: String?=null,
    val newsUrl: String?=null

)