package com.example.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamecompanion.R
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
        MobileAds.initialize(this){}
        val adRequest= AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)



        bottomNavigationView.setOnNavigationItemSelectedListener{ menuItem ->
            //Switch menu item id
            when(menuItem.itemId){
                R.id.chat ->{

                    FirebaseAnalytics.getInstance(this).logEvent("Chat_Tab_Click", null)
                }
                R.id.feed ->{
                    FirebaseAnalytics.getInstance(this).logEvent("News_Tab_Click", null)
                }
                R.id.profile ->{
                    //Create Fragment
                    val profileFragment = ProfileFragment()
                    //Add Fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.add(fragmentContainer.id, profileFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("Profile_Tab_Click", null)
                }
                R.id.friends ->{
                    FirebaseAnalytics.getInstance(this).logEvent("Friends_Tab_Click", null)
                }


            }
           true
        }
    }
}
