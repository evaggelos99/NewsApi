package com.example.coursework.ui.topics

import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.ArticleArray
import com.example.coursework.MyAdapter
import com.example.coursework.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class TopicsFragment : Fragment() {

    val BASE_URL = "http://newsapi.org/v2"
    val EVERYTHING = "/everything?"
    val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
    val prefArray = arrayListOf<Any>()
    val TOP_HEADLINES = "/top-headlines?"

    private lateinit var topicsViewModel: TopicsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_topics, container, false)
        val buttonGenerateTopics: Button = root.findViewById(R.id.button_generate_news)
        val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        prefArray.add(preferences!!.getBoolean("entertainment", false))
        prefArray.add("entertainment")
        prefArray.add(preferences.getBoolean("business", false))
        prefArray.add("business")
        prefArray.add(preferences.getBoolean("science", false))
        prefArray.add("science")
        prefArray.add(preferences.getBoolean("health", false))
        prefArray.add("health")
        prefArray.add(preferences.getBoolean("sports", false))
        prefArray.add("sports")
        prefArray.add(preferences.getBoolean("technology", false))
        prefArray.add("technology")
        val preferredCountry = (preferences.getString("preferred_country", "gb"))



            buttonGenerateTopics.setOnClickListener {
                var categoryURL = ""
                var x = 0
                while (x < prefArray.size - 1 || x < prefArray.size) {
                    if (prefArray[x] == true ) {
                        categoryURL = categoryURL + "category="+prefArray[x + 1].toString() + "&"
                        x += 2
                    } else {
                        x += 2
                    }
                }
                val request = Request.Builder()
                    .url(BASE_URL + TOP_HEADLINES +"country="+preferredCountry+"&"+categoryURL + "pageSize=10&" + API_KEY).build()

                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()
                        val articles = gson.fromJson(body, ArticleArray::class.java)
                        val recyclerViewLayout =
                            root.findViewById(R.id.recyclerView_topics_layout) as RecyclerView

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