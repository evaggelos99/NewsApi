package com.example.coursework.ui.topics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class TopicsFragment : Fragment(), onItemClickListener {

    val BASE_URL = "http://newsapi.org/v2"
    val EVERYTHING = "/everything?"
    val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
    val prefArray = arrayListOf<Any>()
    val TOP_HEADLINES = "/top-headlines?"

    private lateinit var layout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_topics, container, false)

        return root
        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context,"Swipe down to refresh your topics!", Toast.LENGTH_LONG).show()
        //val buttonGenerateTopics: Button = view.findViewById(R.id.button_generate_news)
        layout = view.findViewById(R.id.topic_layout)

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
        if (preferredCountry != null) {
            swipeDownListener(preferredCountry, view)

        }



    }



    private fun getCategoryUrl(): String {
        var categoryURL1 = ""
        var x = 0
        while (x < prefArray.size - 1 || x < prefArray.size) {
            if (prefArray[x] == true) {
                categoryURL1 = categoryURL1 + "category=" + prefArray[x + 1].toString() + "&"
                x += 2
            } else {
                x += 2
            }
        }
        return categoryURL1
    }

    override fun onItemClick(item : Article, position: Int) {
        val newIntent = Intent(Intent.ACTION_VIEW)
        newIntent.data = Uri.parse(item.url)
        startActivity(newIntent)
    }

    override fun onSourceAddClick(item: Article, position: Int) {
        Toast.makeText(context,"Your source has been added!", Toast.LENGTH_SHORT).show()
        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        val prevSet = preferences!!.getStringSet("sources", null)

        val set: MutableSet<String> = prevSet as MutableSet<String>
        if (item.source.id==null) {
            set.add(item.source.name)
        } else {
            set.add(item.source.id)
        }
        Toast.makeText(context, item.source.name, Toast.LENGTH_LONG).show()
        editor?.putStringSet("sources", set)
    }
























    private fun swipeDownListener(preferredCountry: String, view: View) {
        layout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                //val swipeUpInfo = view?.findViewById(R.id.swipe_up_information) as TextView
                //swipeUpInfo.visibility= View.GONE
                val categoryURL = getCategoryUrl()
                val request = Request.Builder()
                        .url(BASE_URL + TOP_HEADLINES +"country="+preferredCountry+"&"+categoryURL + "pageSize=10&" + API_KEY).build()

                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {}

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()
                        val articles = gson.fromJson(body, ArticleArray::class.java)
                        val recyclerViewLayout =
                                view?.findViewById(R.id.recyclerView_topics_layout) as RecyclerView

                        activity?.runOnUiThread {
                            val llm = LinearLayoutManager(context)
                            llm.orientation = LinearLayoutManager.VERTICAL
                            recyclerViewLayout.setLayoutManager(llm)
                            recyclerViewLayout.setAdapter(MyAdapter(articles,this@TopicsFragment))

                        }



                    }



                })


            }


        })


    }


}