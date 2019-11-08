package com.example.gamecompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setOnNavigationItemSelectedListener{ menuItem ->
            when(menuItem.itemId){
                R.id.chat->{

                }
                R.id.feed->{

                }
                R.id.profile->{
                    //Create Fragment
                    val profileFragment = ProfileFragment()
                    //Add Fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.add(fragmentContainer.id, profileFragment)
                    fragmentTransaction.commit()
                }
                R.id.friends->{

                }


            }
           true
        }
    }
}
