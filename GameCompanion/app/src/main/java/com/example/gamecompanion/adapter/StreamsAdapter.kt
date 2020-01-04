package com.example.gamecompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamecompanion.R
import com.example.gamecompanion.model.StreamModel
import io.grpc.internal.Stream
import kotlinx.android.synthetic.main.item_stream.view.*

class StreamsAdapter(var list: List<StreamModel>): RecyclerView.Adapter<StreamsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.title
        val description: TextView = itemView.description
        val imageView: ImageView = itemView.imageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stream, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = list[position]

        //val s : String = new.find { "username" }
        holder.title.text = new.title
        holder.description.text= new.username
        Glide.with(holder.imageView.context).load(list[position].getThumbnailImageUrl()).into(holder.imageView)

    }



}