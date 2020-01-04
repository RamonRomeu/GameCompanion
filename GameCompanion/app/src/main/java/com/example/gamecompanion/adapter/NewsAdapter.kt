package com.example.gamecompanion.adapter

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
        val title: TextView = item.title
        val description: TextView = item.description
        val imageView: ImageView = item.imageView
    }
}