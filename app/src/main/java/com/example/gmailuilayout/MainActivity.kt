package com.example.gmailuilayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gmailuilayout.databinding.ActivityMainBinding
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            searchBar.setNavigationIcon(R.drawable.outline_menu_24) // Replace with your own icon
            Glide.with(this@MainActivity).load("https://randomuser.me/api/portraits/women/9.jpg").into(rcvUsersImage)


            setCurrentFragment(MailsFragment())
            bottomNavBar.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.mails -> {
                        setCurrentFragment(MailsFragment())
                        true
                    }

                    else ->{
                        setCurrentFragment(MeetFragment())
                        true
                    }
                }
            }
        }



    }


    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

}