package com.example.gamecompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamecompanion.R
import com.example.gamecompanion.model.ChatMessage
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(var list: List<ChatMessage>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textview = itemView.textview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textview.text = list[position].text
    }
}