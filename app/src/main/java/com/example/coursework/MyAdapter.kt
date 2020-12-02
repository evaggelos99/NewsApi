package com.example.coursework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val articleArray: ArticleArray, var clickListener : onItemClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle = itemView.findViewById(R.id.textViewTitle) as TextView
        val textViewDescription = itemView.findViewById(R.id.textViewDescription) as TextView
        val image = itemView.findViewById(R.id.articleImage) as ImageView
        val textViewSource = itemView.findViewById(R.id.textViewSource) as TextView
        val textViewAuthor = itemView.findViewById(R.id.textViewAuthor) as TextView

        fun init(item : Article, action: onItemClickListener) {
            textViewTitle.text = item.title
            textViewDescription.text = item.description
            Picasso.get().load(item.urlToImage).into(image)


            val gg = item.source.toString()
            textViewSource.text = item.source.name
            textViewAuthor.text = item.author



            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
            itemView.setOnLongClickListener {
                action.onSourceAddClick(item,adapterPosition)
                true
            }



        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val article: Article = articleArray.articles[position] //articleArray.articles[position]
        holder.textViewTitle.text = article.title
        holder.textViewDescription.text = article.description
        Picasso.get().load(article.urlToImage).into(holder.image);
        holder.textViewAuthor.text = article.author
        val gg = article.source.toString()
        holder.textViewSource.text = gg.replace("{id=", "")
            .replace(", name=", "\n")
            .replace("}","")
            .replace("null","")*/

        holder.init(articleArray.articles.get(position), clickListener)

    }

    override fun getItemCount(): Int {
        return articleArray.articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_row, parent, false)



        return ViewHolder(v)
    }


}


interface  onItemClickListener{

    fun onItemClick(item : Article, position: Int) {

    }

    fun onSourceAddClick(item : Article, position: Int) {

    }
}