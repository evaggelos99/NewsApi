package com.example.coursework.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.example.coursework.ui.topics.OnSwipeTouchListener
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class HomeFragment : Fragment(), onItemClickListener {

    val BASE_URL = "https://newsapi.org/v2"
    val EVERYTHING = "/everything?"
    val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
    val prefArray = arrayListOf<Any>()
    val TOP_HEADLINES = "/top-headlines?"

    private lateinit var layout : LinearLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = view.findViewById(R.id.home_layout)

        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val sourceSet = preferences?.getStringSet("sources", null)

        if (sourceSet != null) {
            swipeDownListener(sourceSet,view)
        }

    }

    private fun getSourceUrl(set : MutableSet<String>) : String {
        var sourceString = "sources="
        if (set.isEmpty()) {
            Toast.makeText(context, "No sources select. Country default set to UK", Toast.LENGTH_SHORT).show()
            return "country=gb&"
        }
        for (source in set) {
            var currentSource = source.filter { !it.isWhitespace() }
            currentSource = currentSource+","

            //currentSource+=".com,"
            //currentSource+=","
            sourceString+=currentSource
        }

        sourceString = sourceString.substring(0,sourceString.length - 1)
        sourceString+= "&"
        return sourceString
    }

    private fun swipeDownListener(set: MutableSet<String>, view: View) {
        val sourceUrl = getSourceUrl(set)
        layout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                val request = Request.Builder()
                    .url(BASE_URL + TOP_HEADLINES + sourceUrl+"pageSize=10&" + API_KEY).build()
                println(BASE_URL + TOP_HEADLINES + sourceUrl + API_KEY)
                Toast.makeText(context, BASE_URL + TOP_HEADLINES + sourceUrl + API_KEY, Toast.LENGTH_LONG).show()
                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {}

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()
                        val articles = gson.fromJson(body, ArticleArray::class.java)
                        val recyclerViewLayout =
                            view?.findViewById(R.id.recyclerView_home) as RecyclerView

                        activity?.runOnUiThread {
                            val llm = LinearLayoutManager(context)
                            llm.orientation = LinearLayoutManager.VERTICAL
                            recyclerViewLayout.setLayoutManager(llm)
                            recyclerViewLayout.setAdapter(MyAdapter(articles,this@HomeFragment))

                        }


                    }



                })


            }


        })


    }

    override fun onItemClick(item: Article, position: Int) {
        val newIntent = Intent(Intent.ACTION_VIEW)
        newIntent.data = Uri.parse(item.url)
        startActivity(newIntent)
    }



}