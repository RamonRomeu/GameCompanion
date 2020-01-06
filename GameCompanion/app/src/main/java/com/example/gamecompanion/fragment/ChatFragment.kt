package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.LoginActivity
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.adapter.ChatAdapter
import com.example.gamecompanion.model.ChatMessage
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_CHAT
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_chat.*
import java.io.ByteArrayOutputStream
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }


    private val adapter= ChatAdapter(emptyList())
    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init UI
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //Get Chats
        subscribeToMessages()

        //Send Message
        sendButton.setOnClickListener{
            val text= messageEditText.text.toString()
            if(text.isNotBlank()){
                sendMessage(text)
                messageEditText.text.clear()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        //initUI()
    }

    private fun subscribeToMessages() {
        FirebaseFirestore.getInstance()
            .collection(COLECTION_CHAT)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                //Check fragment exists
                if(!isAdded) return@addSnapshotListener

                //Get messages from collection
                val messages = querySnapshot?.documents
                    ?.map { it.toObject(ChatMessage::class.java) ?: ChatMessage() }
                    ?: emptyList()

                var i = querySnapshot?.documents

                var messagesOrdered : List<ChatMessage> = messages.sortedWith(compareBy({it.timestamp}))

                //Update List
                adapter.list = messagesOrdered
                adapter.notifyDataSetChanged()
            }

    }



    private fun sendMessage(text: String){
        //Prepare Model

        var userIdVar = FirebaseAuth.getInstance().currentUser?.uid
        val chatMessage = ChatMessage(text = text, timestamp = System.currentTimeMillis(), userId = userIdVar, document = "")
        //Send Message
        FirebaseFirestore.getInstance()
            .collection(COLECTION_CHAT)
            .add(chatMessage)
            .addOnSuccessListener {

                //Update Document online
                FirebaseFirestore.getInstance()
                    .collection(COLECTION_CHAT)
                    .document(it.id)
                    .update("document", it.id)

                //Update userName online
                FirebaseFirestore.getInstance()
                    .collection(COLECTION_CHAT)
                    .document(it.id)
                    .update("userId", SharePreferencesManager().getUsername(requireContext()))


                //Get userPic

                var s = ""

                FirebaseFirestore.getInstance()
                    .collection(COLECTION_USERS)
                    .document(it.id)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        s = documentSnapshot.get("profilePicture").toString()
                    }

                //Update UserPic online
                FirebaseFirestore.getInstance()
                    .collection(COLECTION_CHAT)
                    .document(it.id)
                    .update("pictureUrl", s)

                Log.i("ChatFragment", "MessageAdded")
                //subscribeToMessages()
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }

}