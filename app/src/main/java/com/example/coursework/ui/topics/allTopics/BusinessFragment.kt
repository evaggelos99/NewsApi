package com.example.coursework.ui.topics.allTopics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.example.coursework.Adapters.Article
import com.example.coursework.Adapters.ArticleArray
import com.example.coursework.Adapters.MyAdapter
import com.example.coursework.Adapters.onItemClickListener
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class BusinessFragment : Fragment(),
    onItemClickListener {


    val BASE_URL = "https://newsapi.org/v2"
    val TOP_HEADLINES="/top-headlines?"
    val API_KEY="apiKey=bb5340ea2839447eb75d2e5515ab6081"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val bool = preferences?.getBoolean("business", false)
        val preferredCountry = preferences?.getString("preferred_country", "gb")
        if (bool!!) {
            val request = Request.Builder()
                    .url(BASE_URL + TOP_HEADLINES + "country=" + preferredCountry + "&" + "category=business&" + API_KEY)
                    .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val articles = gson.fromJson(body, ArticleArray::class.java)
                    val recyclerViewLayout =
                            view.findViewById(R.id.business_recView) as RecyclerView

                    activity?.runOnUiThread {
                        val llm = LinearLayoutManager(context)
                        llm.orientation = LinearLayoutManager.VERTICAL
                        recyclerViewLayout.setLayoutManager(llm)
                        recyclerViewLayout.setAdapter(
                            MyAdapter(
                                articles,
                                this@BusinessFragment
                            )
                        )

                    }


                }


            })
        } else {
            Toast.makeText(context, "You are not subscribed to this topic", Toast.LENGTH_SHORT)
                .show()
        }



    }

    override fun onItemClick(item: Article, position: Int) {
        val preferences = activity?.getSharedPreferences("clicks", Context.MODE_PRIVATE)
        var clicks = preferences?.getInt("businessClicks", 0)
        val editor = preferences?.edit()
        if (clicks != null) {
            editor?.putInt("businessClicks", clicks+1)
            editor?.commit()
        }

        val newIntent = Intent(Intent.ACTION_VIEW)
        newIntent.data = Uri.parse(item.url)
        startActivity(newIntent)
    }
}