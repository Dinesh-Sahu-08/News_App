package com.example.newsapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsListAdapter(private val listener:newsItemClicked):RecyclerView.Adapter<newsViewHolder>() {

    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=newsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {

        val currentItems=items[position]
        holder.titleView.text=currentItems.title
        holder.author.text=currentItems.author
        Log.e("msg","author ${currentItems.author}")
        Glide.with(holder.itemView.context).load(currentItems.imageUrl).into(holder.image)
    }

    fun updateNews(updatedNews:ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image:ImageView = itemView.findViewById(R.id.image)
    val author:TextView = itemView.findViewById(R.id.author)
}

interface newsItemClicked{
    fun onItemClicked(items: News)

}