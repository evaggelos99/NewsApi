package com.example.coursework.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.ArticleArray
import com.example.coursework.MyAdapter
import com.example.coursework.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SearchFragment : Fragment() {


    val BASE_URL ="http://newsapi.org/v2"
    val TOP_HEADLINES = "/top-headlines?"
    val EVERYTHING = "/everything?"
    val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
    //params
    val COUNTRY = "country="
    val CATEGORY = "category="
    //val KEYWORD_PHRASE = "q="
    val PAGE = "page"
    val PAGE_SIZE = "pageSize"

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchButton: Button = root.findViewById(R.id.search_button)



        searchButton.setOnClickListener {

            val spinnerCountry : Spinner = root.findViewById(R.id.country_list_spinner)
            var country = spinnerCountry.selectedItem.toString().
            substring(spinnerCountry.selectedItem.toString().length-2,
                    spinnerCountry.selectedItem.toString().length)
            if (country=="ne") {
                country=""
            } else country = "$COUNTRY$country&"

            val spinnerCategory : Spinner = root.findViewById(R.id.category_list_spinner)
            var category = spinnerCategory.selectedItem.toString()
            if (category=="None") {
                category=""
            } else category = "$CATEGORY$category&"

            val keywordInput : EditText = root.findViewById(R.id.Keyword_Input)

            var keyword = keywordInput.text.toString()

            if (keyword=="") {
                keyword = ""
            } else keyword= "q=$keyword&"

            //var articleClass : ArticleArray? = null
            //val request = Request.Builder().url(
            //        BASE_URL+secondParam+KEYWORD_PHRASE+keywordInput.text.toString()+"&"+API_KEY).build()
            val request = Request.Builder().url(BASE_URL+TOP_HEADLINES+
                    country+category + keyword +API_KEY).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {

                }


                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val articles = gson.fromJson(body, ArticleArray::class.java)
                    val recyclerViewLayout = root.findViewById(R.id.recyclerView_search_layout) as RecyclerView

                    activity?.runOnUiThread {
                        val llm = LinearLayoutManager(context)
                        llm.orientation = LinearLayoutManager.VERTICAL
                        recyclerViewLayout.setLayoutManager(llm)
                        recyclerViewLayout.setAdapter(MyAdapter(articles))
                    }
                }


            })
        }
        return root
    }

}