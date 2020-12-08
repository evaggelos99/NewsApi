package com.example.coursework

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class WidgetService() : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ItemFactor(applicationContext, intent)
    }

    class ItemFactor(val context: Context, val intent : Intent) : RemoteViewsFactory, onItemClickListener {

        lateinit var articles : ArticleArray
        val BASE_URL = "https://newsapi.org/v2"
        val API_KEY = "apiKey=bb5340ea2839447eb75d2e5515ab6081"
        val TOP_HEADLINES = "/top-headlines?"
        override fun onCreate() {
            /*val businessClicks = preferences?.getInt("businessClicks", 0)
            val generalClicks = preferences?.getInt("generalClicks", 0)
            val entertainmentClicks = preferences?.getInt("entertainmentClicks", 0)
            val healthClicks = preferences?.getInt("healthClicks", 0)
            val scienceClicks = preferences?.getInt("scienceClicks", 0)
            val sportsClicks = preferences?.getInt("sportsClicks", 0)
            val technologyClicks = preferences?.getInt("technologyClicks", 0)
            val totalClicks = preferences?.getInt("totalClicks", 0)
            val preferredCountry = preferences?.getString("preferred_country", "gb")


            val topicsInUse = getAllTopicsInUser()

            val averagePercentage = totalClicks?.div(topicsInUse)
            val businessClicksAverage = totalClicks?.let { businessClicks?.div(it) }
            val generalClicksAverage = totalClicks?.let { generalClicks?.div(it) }
            val entertainmentClicksAverage = totalClicks?.let { entertainmentClicks?.div(it) }
            val healthClicksAverage = totalClicks?.let { healthClicks?.div(it) }
            val scienceClicksAverage = totalClicks?.let { scienceClicks?.div(it) }
            val sportsClicksAverage = totalClicks?.let { sportsClicks?.div(it) }
            val technologyClicksAverage = totalClicks?.let { technologyClicks?.div(it) }

            if (businessClicksAverage!! > averagePercentage!!) {

            }
            if (generalClicksAverage!! > averagePercentage) {}

            if (entertainmentClicksAverage!! > averagePercentage) {}
            if (healthClicksAverage!! > averagePercentage) {}
            if (scienceClicksAverage!! > averagePercentage) {}
            if (sportsClicksAverage!! > averagePercentage) {}
            if (technologyClicksAverage!! > averagePercentage) {}
*/          /*val preferences = context.getSharedPreferences("clicks", Context.MODE_PRIVATE)
            val sourceSet = preferences?.getStringSet("sources", null)



            val request = Request.Builder()
                .url(BASE_URL + TOP_HEADLINES + sourceSet?.let { getSourceUrl(it) } +"pageSize=10&" + API_KEY).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    articles = gson.fromJson(body, ArticleArray::class.java)


                }



            })*/
            val preferences = context.getSharedPreferences("clicks", Context.MODE_PRIVATE)
            val prevSet : MutableSet<String> = HashSet()
            var sourceSet = preferences?.getStringSet("sources", prevSet)

            val stn =BASE_URL + TOP_HEADLINES + getSourceUrl(sourceSet!!) +"pageSize=10&" + API_KEY
            Log.e("widget",stn)


            val request = Request.Builder()
                .url(BASE_URL + TOP_HEADLINES + getSourceUrl(sourceSet!!) +"pageSize=10&" + API_KEY).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    articles = gson.fromJson(body, ArticleArray::class.java)


                }



            })
            Thread.sleep(2000)
        }

        private fun getSourceUrl(set : MutableSet<String>) : String {
            var sourceString = "sources="
            if (set.isEmpty()) {
                return "country=gb&"
            }

            for (source in set) {
                var currentSource = source.filter { !it.isWhitespace() }
                currentSource = "$currentSource,"

                //currentSource+=".com,"
                //currentSource+=","
                sourceString+=currentSource
            }

            sourceString = sourceString.substring(0,sourceString.length - 1)
            sourceString+= "&"
            return sourceString
        }


        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onDataSetChanged() {
            val preferences = context.getSharedPreferences("sources", Context.MODE_PRIVATE)
            val prevSet : MutableSet<String> = HashSet()
            var sourceSet = preferences?.getStringSet("sources", prevSet)

            val request = Request.Builder()
                .url(BASE_URL + TOP_HEADLINES + getSourceUrl(sourceSet!!) +"pageSize=10&" + API_KEY).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    articles = gson.fromJson(body, ArticleArray::class.java)


                }



            })
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.widget_item)
            views.setTextViewText(R.id.widget_title, articles.articles[position].title)
            views.setTextViewText(R.id.widget_source, articles.articles[position].source.name)
            if (!articles.articles[position].urlToImage.isNullOrEmpty()) {
                val image: Bitmap = Picasso.get().load(articles.articles[position].urlToImage).get()
                views.setImageViewBitmap(R.id.widget_image, image)
            }
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.putExtra(ArticleFeedWidget.EXTRA_ITEM_POSITION, articles.articles[position].url)
            newIntent.putExtra("url",articles.articles[position].url)
            //newIntent.data = Uri.parse(articles.articles[position].url)

            views.setOnClickFillInIntent(R.id.widget_item, newIntent)

            return views
        }

        override fun getCount(): Int {
             return articles.articles.size
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {
        }



        private fun getAllTopicsInUser() : Int {
            var preferences1 = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val businessClicksBool = preferences1?.getBoolean("business", false)
            val generalClicksBool = preferences1?.getBoolean("general", false)
            val entertainmentClicksBool = preferences1?.getBoolean("entertainment", false)
            val healthClicksBool = preferences1?.getBoolean("health", false)
            val scienceClicksBool = preferences1?.getBoolean("science", false)
            val sportsClicksBool = preferences1?.getBoolean("sports", false)
            val technologyClicksBool = preferences1?.getBoolean("technology", false)

            var topicsInUse=0

            if (businessClicksBool == true) {
                topicsInUse += 1
            }
            if (generalClicksBool == true) {
                topicsInUse += 1
            }
            if (entertainmentClicksBool == true) {
                topicsInUse += 1
            }
            if (healthClicksBool == true) {
                topicsInUse += 1
            }
            if (scienceClicksBool == true) {
                topicsInUse += 1
            }
            if (sportsClicksBool == true) {
                topicsInUse += 1
            }
            if (technologyClicksBool == true) {
                topicsInUse += 1
            }
            return topicsInUse
        }



    }

}