package com.example.gmailuilayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gmailuilayout.databinding.ActivityMainBinding
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rcvEmailsAdapter: RcvEmailsAdapter
    private lateinit var emailsListViewModel: EmailsListViewModel

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            if (position != RecyclerView.NO_POSITION) {

                emailsListViewModel.removeEmailWithId(position)

                rcvEmailsAdapter.notifyItemRemoved(position)
                rcvEmailsAdapter.notifyItemRangeChanged(position, emailsListViewModel.emailsList.value!!.size)
                Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        emailsListViewModel = EmailsListViewModel()

        rcvEmailsAdapter = RcvEmailsAdapter(emailsListViewModel.emailsList.value!!)

        binding.apply {
            searchBar.setNavigationIcon(R.drawable.outline_menu_24) // Replace with your own icon
            Glide.with(this@MainActivity).load("https://randomuser.me/api/portraits/women/9.jpg").into(rcvUsersImage)
            ItemTouchHelper(swipeCallback).attachToRecyclerView(rcvEmails)
            rcvEmails.adapter = rcvEmailsAdapter
            rcvEmails.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)


        }

    }

}