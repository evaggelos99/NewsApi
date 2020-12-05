package com.example.coursework.ui.account.ui

import android.os.Bundle
import android.view.*
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class AddFeedsFragment : Fragment(), onItemClickListener {
    var adapt : FeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_add_feeds, container, false)
        //val editTextAdd : SearchView = root.findViewById(R.id.search_sources)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewLayout =
            view.findViewById(R.id.add_frag_recView) as RecyclerView
        val request = Request.Builder()
            .url("https://newsapi.org/v2/sources?apiKey=bb5340ea2839447eb75d2e5515ab6081").build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val feeds = gson.fromJson(body, Feed::class.java)


                activity?.runOnUiThread {
                    val llm = LinearLayoutManager(context)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    recyclerViewLayout.setLayoutManager(llm)
                    adapt = FeedAdapter(feeds)
                    recyclerViewLayout.setAdapter(adapt)
                }



            }



        })

        val item = object : SwipeToDelete(context, 0, ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                context?.let { adapt?.add(viewHolder.adapterPosition, it) }
            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recyclerViewLayout)
    }


   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapt?.filter?.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapt?.filter?.filter(newText)
                ( as Filterable).filter.filter(newText)
                return true
            }

        })
        //super.onCreateOptionsMenu(menu, inflater)

    }*/

}