package com.example.gamecompanion.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gamecompanion.R
import kotlinx.android.synthetic.main.activity_newsdetails.*
import kotlinx.android.synthetic.main.item_chat.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.android.synthetic.main.item_news.description


class NewsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsdetails)

        val thumbnail:String

        if(intent.hasExtra("title") &&
            intent.hasExtra("descriptionFull") &&
            intent.hasExtra("thumbnail"))
        {
            titleNew.text = intent.getStringExtra("title")
            description.text = intent.getStringExtra("descriptionFull")
            thumbnail = intent.getStringExtra("thumbnail")

            Glide.with(this).load(thumbnail).into(imageViewNew)

        }


    }
}