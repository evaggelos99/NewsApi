package com.example.coursework.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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
    val SOURCES = "sources="
    val KEYWORD_PHRASE = "q="
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

        val keywordInput : EditText = root.findViewById(R.id.Keyword_Input)
        //category and country spinner

        val headlineCheckBox : CheckBox = root.findViewById(R.id.headline_checkbox)
        //if it's checked then it fetches top stories else everything

        searchButton.setOnClickListener {
            val secondParam = if (!headlineCheckBox.isChecked){
                TOP_HEADLINES
            } else {
                EVERYTHING
            }
            //var articleClass : ArticleArray? = null
            val request = Request.Builder().url(BASE_URL+secondParam+KEYWORD_PHRASE+keywordInput.text.toString()+"&"+API_KEY).build()
            println(BASE_URL+secondParam+KEYWORD_PHRASE+keywordInput.text.toString()+"&"+API_KEY)
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {

                }


                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val articles = gson.fromJson(body, ArticleArray::class.java)
                    val recyclerViewLayout = root.findViewById(R.id.recyclerView_search_layout) as RecyclerView

                    //recyclerViewLayout.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    activity?.runOnUiThread {
                        val llm = LinearLayoutManager(context)
                        llm.orientation = LinearLayoutManager.VERTICAL
                        recyclerViewLayout.setLayoutManager(llm)
                        recyclerViewLayout.setAdapter(MyAdapter(articles))
                    }
                    //activity?.runOnUiThread {
                    //    recyclerViewLayout.adapter=MyAdapter(articles)
                    //}

                    //childFragmentManager.beginTransaction().replace(R.id.fragment_search, ArticleListFragment)
                    //root = inflater.inflate(R.layout.recyclerView_search_layout,container,false)
                    // recyclerview.adapter = myadapter(articleArray)
                    // return the json request thingy probably you
                    // dont need the adapter it should be done in the fragment
                }


            })
        }
        return root
    }

}