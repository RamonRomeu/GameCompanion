package com.example.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamecompanion.R
import com.example.gamecompanion.fragment.ChatFragment
import com.example.gamecompanion.fragment.FriendsFragment
import com.example.gamecompanion.fragment.NewsFragment
import com.example.gamecompanion.fragment.ProfileFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Load Ad
     //   MobileAds.initialize(this){}
       // val adRequest= AdRequest.Builder().build()
     //   bannerAdView.loadAd(adRequest)

        //Create Fragment
        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()
        val newsFragment = NewsFragment();
        val friendsFragment = FriendsFragment();

        val fragmentManager = supportFragmentManager

        bottomNavigationView.setOnNavigationItemSelectedListener{ menuItem ->
            //Switch menu item id
            when(menuItem.itemId){
                R.id.chat ->{
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, chatFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("Chat_Tab_Click", null)
                }
                R.id.news ->{
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, newsFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("News_Tab_Click", null)
                }
                R.id.profile ->{
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, profileFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("Profile_Tab_Click", null)
                }
                R.id.friends ->{
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, friendsFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("Friends_Tab_Click", null)
                }


            }
           true
        }
        bottomNavigationView.selectedItemId = R.id.profile
    }
}
