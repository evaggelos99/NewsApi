package com.example.coursework

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class JsonRequest {

    val BASE_URL ="http://newsapi.org/v2"
    val TOP_HEADLINES = "/top-headlines?"
    val EVERYTHING = "/everything?"
    val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
    //params
    val COUNTRY = "country="
    val CATEGORY = "category="
    val SOURCES = "sources="
    val KEYWORD_PHRASE = "q="
    val PAGE = "page"
    val PAGE_SIZE = "pageSize"

    fun requestData(keyWord : String?, headlineCheckBox : Boolean) {
        val secondParam = if (headlineCheckBox){
            TOP_HEADLINES
        } else {
            EVERYTHING
        }
        //var articleClass : ArticleArray? = null
        val request = Request.Builder().url("$BASE_URL$secondParam$KEYWORD_PHRASE$keyWord&$API_KEY").build()
        val client = OkHttpClient()
        //var gson = GsonBuilder().create()
        client.newCall(request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {
                val articleClass =null
            }


            override fun onResponse(call: Call, response: Response) {
                val body= response.body?.string()
                val gson = GsonBuilder().create()

                val articleClass=gson.fromJson(body, ArticleArray::class.java)
                // recyclerview.adapter = myadapter(articleArray)
                // return the json request thingy probably you
                // dont need the adapter it should be done in the fragment
            }


        })
    }
}
