package com.example.coursework

class ArticleArray(val status: String, val totalResults: Int, val articles: List<Article>) {

}

class Article(val source: Any,
              val author: String,
              val title: String,
              val description:String,
              val url:String,
              val urlToImage:String,
              val publishedAt:String,
              val content:String) {

    override fun toString(): String {
    
        return "Article(source=$source)"
    }

}