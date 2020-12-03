package com.example.coursework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FeedAdapter(val feedSourceList: ArrayList<Feed>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewSource = itemView.findViewById(R.id.feed_textView) as TextView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.feed_cardview_row,parent,false)

        return ViewHolder(v)
    }

    fun delete(position: Int, context : Context) {
        val feedTobeRemoved = feedSourceList[position]
        feedSourceList.removeAt(position)
        notifyDataSetChanged()
        val preferences = context?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        val sourceSet = preferences?.getStringSet("sources", null)
        sourceSet?.remove(feedTobeRemoved.feedSource)
        editor?.putStringSet("sources", sourceSet)
        editor?.commit()
    }

    override fun getItemCount(): Int {
        return feedSourceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed : Feed = feedSourceList[position]
        holder.textViewSource.text = feed.feedSource
    }

}

class Feed(val feedSource : String) {

}