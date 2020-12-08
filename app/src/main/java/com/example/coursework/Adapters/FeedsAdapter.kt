package com.example.coursework.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import java.util.*
import kotlin.collections.ArrayList


class FeedAdapter(val feedSourceList : Feed) : RecyclerView.Adapter<FeedAdapter.ViewHolder>(), Filterable {

    var feedList = ArrayList<Sources>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var textViewSource = itemView.findViewById(R.id.feed_textView) as TextView
    }

    init {
        feedList.addAll(feedSourceList.sources)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.feed_cardview_row,parent,false)

        return ViewHolder(v)
    }




    fun add(position: Int, context : Context) {
        val item = feedSourceList.sources[position]
        val preferences = context?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        var set : MutableSet<String> = HashSet()
        val editor = preferences?.edit()
        val prevSet = preferences?.getStringSet("sources", set)

        if (prevSet != null) {
            prevSet.add(item.id)
            Toast.makeText(context, "Your source has been added!", Toast.LENGTH_SHORT).show()
            editor?.remove("sources")
            editor?.commit()
            editor?.putStringSet("sources", prevSet)
            editor?.commit()
            feedSourceList.sources.removeAt(position)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return feedSourceList.sources.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source : Sources = feedSourceList.sources[position]
        holder.textViewSource.text = source.name
    }

    override fun getFilter(): Filter? {
        return executeFilter
    }

    private val executeFilter : Filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filteredList : MutableList<Sources> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList = feedList as ArrayList<Sources>
            } else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (row in filteredList) {
                    if (row.name.toLowerCase().contains(pattern)) {
                        filteredList.add(row)
                    }

                }

            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults?) {
            feedList = results?.values as ArrayList<Sources>
            notifyDataSetChanged()
        }
    }


}

//feedsource was the id
data class Feed(val status : String, val sources: ArrayList<Sources>) {

}

class Sources(val id: String, val name: String,
              val description : String,
              val url : String, val category : String,
              val language : String,
              val country : String) {

    override fun toString(): String {
        return name
    }

}

class FeedSource(val sourceString : String) {

    override fun toString(): String {
        return sourceString
    }

}

class SimpleFeedAdapter(val simpleFeedSourceList : ArrayList<FeedSource>) : RecyclerView.Adapter<SimpleFeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.feed_cardview_row,parent,false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return simpleFeedSourceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source : FeedSource = simpleFeedSourceList[position]
        holder.textViewSource.text = source.toString()
    }

    fun delete(position: Int, context : Context) {
        val feedTobeRemoved = simpleFeedSourceList[position]
        simpleFeedSourceList.removeAt(position)
        val preferences = context?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        val set : MutableSet<String> = HashSet()
        val sourceSet = preferences?.getStringSet("sources", set)
        sourceSet?.remove(feedTobeRemoved.toString())
        editor?.remove("sources")
        editor?.commit()
        editor?.putStringSet("sources", sourceSet)
        notifyDataSetChanged()
        editor?.commit()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var textViewSource = itemView.findViewById(R.id.feed_textView) as TextView
    }

}