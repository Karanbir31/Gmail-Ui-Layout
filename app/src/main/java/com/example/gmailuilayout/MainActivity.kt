package com.example.gmailuilayout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
    private val emailsListViewModel : EmailsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {

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

    fun setAppBarAndBottomNavVisible(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        findViewById<View>(R.id.bottomNavBar).visibility = visibility
       // findViewById<View>(R.id.mainAppBar).visibility = visibility
    }

}