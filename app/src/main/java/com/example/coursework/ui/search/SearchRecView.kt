package com.example.coursework.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SearchRecView : Fragment(), onItemClickListener{



    val BASE_URL = "https://newsapi.org/v2"
    val TOP_HEADLINES="/top-headlines?"
    val API_KEY="apiKey=bb5340ea2839447eb75d2e5515ab6081"
    lateinit var layout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_search_rec_view, container, false)
        layout = root.findViewById(R.id.search_recView_layout)
        val bundle = this.arguments
        val country= bundle?.getString("country")
        val category=bundle?.getString("category")
        val keyword=bundle?.getString("keyword")


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
                val recyclerViewLayout = root.findViewById(R.id.search_recView) as RecyclerView

                activity?.runOnUiThread {
                    val llm = LinearLayoutManager(context)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    recyclerViewLayout.setLayoutManager(llm)
                    recyclerViewLayout.setAdapter(MyAdapter(articles,this@SearchRecView))
                }
            }


        })
        return root
    }

    override fun onItemClick(item: Article, position: Int) {
        val newIntent = Intent(Intent.ACTION_VIEW)
        newIntent.data = Uri.parse(item.url)
        startActivity(newIntent)
    }

    /*override fun onSourceAddClick(item: Article, position: Int) {
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


    }*/

}