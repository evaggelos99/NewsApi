package com.example.coursework.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SearchFragment : Fragment(), onItemClickListener {

    //TODO Make the xml prettier maybe commit another fragment to display the recycler yes maybe!
    //TODO Make notifications. Look into sleep mode. NOTIFICATIONS ONLY ON TOPICS SELECTED BY USER
    val BASE_URL ="https://newsapi.org/v2"
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
            var country = countryString(spinnerCountry)

            val spinnerCategory : Spinner = root.findViewById(R.id.category_list_spinner)
            var category = categoryString(spinnerCategory)

            val keywordInput : EditText = root.findViewById(R.id.Keyword_Input)

            var keyword = keywordInput.text.toString()

            if (keyword=="") {
                keyword = ""
            } else keyword= "q=$keyword&"

            val request = Request.Builder().url(BASE_URL+TOP_HEADLINES+
                    country+category + keyword +API_KEY).build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Looper.prepare()
                    Toast.makeText(context,e.localizedMessage, Toast.LENGTH_SHORT).show()
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
                        recyclerViewLayout.setAdapter(MyAdapter(articles, this@SearchFragment))
                    }
                }


            })
        }
        return root
    }

    private fun categoryString(spinnerCategory: Spinner): String {
        var category = spinnerCategory.selectedItem.toString()
        if (category == "None") {
            category = ""
        } else category = "$CATEGORY$category&"
        return category
    }

    private fun countryString(spinnerCountry: Spinner): String {
        var country = spinnerCountry.selectedItem.toString().substring(spinnerCountry.selectedItem.toString().length - 2,
                spinnerCountry.selectedItem.toString().length)
        if (country == "ne") {
            country = ""
        } else country = "$COUNTRY$country&"
        return country
    }

    override fun onItemClick(item: Article, position: Int) {
        val newIntent = Intent(Intent.ACTION_VIEW)
        newIntent.data = Uri.parse(item.url)
        startActivity(newIntent)
    }

    override fun onSourceAddClick(item: Article, position: Int) {
        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        var set : MutableSet<String> = HashSet()
        val editor = preferences?.edit()
        val prevSet = preferences?.getStringSet("sources", set)

            if (prevSet != null) {
                if (item.source.id !=null) {
                    prevSet.add(item.source.id)
                    Toast.makeText(context, "Your source has been added!", Toast.LENGTH_SHORT).show()
                    editor?.putStringSet("sources", prevSet)
                    editor?.commit()
                } else {
                    Toast.makeText(context, "This source is not available to be added", Toast.LENGTH_SHORT).show()
                }
            }


    }
}