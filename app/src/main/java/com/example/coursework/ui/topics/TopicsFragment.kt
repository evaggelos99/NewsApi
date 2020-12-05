package com.example.coursework.ui.topics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.example.coursework.ui.search.SearchRecView
import com.example.coursework.ui.topics.allTopics.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class TopicsFragment : Fragment(), onItemClickListener {

    val BASE_URL = "https://newsapi.org/v2"
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

        val businessBtn : Button = view.findViewById(R.id.business)
        val entertainmentBtn : Button = view.findViewById(R.id.entertainment)
        val generalBtn : Button = view.findViewById(R.id.general)
        val healthBtn : Button = view.findViewById(R.id.health)
        val scienceBtn : Button = view.findViewById(R.id.science)
        val sportsBtn : Button = view.findViewById(R.id.sports)
        val technologyBtn : Button = view.findViewById(R.id.technology)

        businessBtn.setOnClickListener {
            val newFrag = BusinessFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        entertainmentBtn.setOnClickListener {
            val newFrag = EntertainmentFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        generalBtn.setOnClickListener {
            val newFrag = GeneralFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        healthBtn.setOnClickListener {
            val newFrag = HealthFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        scienceBtn.setOnClickListener {
            val newFrag = ScienceFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        sportsBtn.setOnClickListener {
            val newFrag = SportsFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        technologyBtn.setOnClickListener {
            val newFrag = TechnologyFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }





        /*Toast.makeText(context,"Swipe down to refresh your topics!", Toast.LENGTH_LONG).show()
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

        }*/



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
        /*val preferences = activity?.getSharedPreferences("clicks", Context.MODE_PRIVATE)
        val preferencesBusiness = activity?.getSharedPreferences(item.cat, Context.MODE_PRIVATE)
        val preferencesEntertainment = activity?.getSharedPreferences("clicksEntertainment", Context.MODE_PRIVATE)
        val preferencesGeneral = activity?.getSharedPreferences("clicksGeneral", Context.MODE_PRIVATE)
        val preferencesHealth = activity?.getSharedPreferences("clicksHealth", Context.MODE_PRIVATE)
        val preferencesScience = activity?.getSharedPreferences("clicksScience", Context.MODE_PRIVATE)
        val preferencesSports = activity?.getSharedPreferences("clicksSports", Context.MODE_PRIVATE)
        val preferencesTechnology = activity?.getSharedPreferences("clicksTechnology", Context.MODE_PRIVATE)


        val editor = preferences?.edit()
        preferences?.getInt("totalClicks", 0)

        editor.putStringSet()*/
    }

    /*override fun onSourceAddClick(item: Article, position: Int) {
        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        var set : MutableSet<String> = HashSet()
        val prevSet = preferences!!.getStringSet("sources", set)




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
























    /*private fun swipeDownListener(preferredCountry: String, view: View) {
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


    }*/


}