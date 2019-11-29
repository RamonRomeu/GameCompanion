package com.example.gamecompanion.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamecompanion.R
import com.example.gamecompanion.model.NewsModel
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * Created by alex on 2019-10-11.
 */

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var list = ArrayList<NewsModel>()


    // Create item_news View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.count()
    }


    // Update Items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = list[position]

        holder.title.text = new.Title
        holder.description.text = new.Description

        Glide.with(holder.imageView.context).load(list[position].imageUrl).into(holder.imageView)
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val title: TextView = item.title // item_joke.xml > @id
        val description: TextView = item.description // item_joke.xml > @id
        val imageView: ImageView = item.imageView // item_joke.xml > @id
    }
}