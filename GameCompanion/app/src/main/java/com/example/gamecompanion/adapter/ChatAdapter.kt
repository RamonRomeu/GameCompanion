package com.example.gamecompanion.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamecompanion.R
import com.example.gamecompanion.model.ChatMessage
import com.example.gamecompanion.util.COLECTION_CHAT
import com.example.gamecompanion.util.COLECTION_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(var list: List<ChatMessage>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textview = itemView.textview
        val cross = itemView.cross
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

        holder.cross.setOnClickListener{

            AlertDialog.Builder(holder.cross.context)
                .setTitle("Hey, listen!!!")
                .setMessage("Are you sure you want to delete the message?")
                .setPositiveButton("*Snaps her/his fingers*") { dialog, buttonId->
                    val id:String = list[position].document.toString()

                    FirebaseFirestore.getInstance()
                        .collection(COLECTION_CHAT)
                        .document(id)
                        .delete()
                }
                .setNegativeButton("Nope") { dialog, buttonId->
                    dialog.dismiss()
                }
                .show()







        }
    }
}