package com.example.gmailuilayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmailuilayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EmailsListViewModel
    private lateinit var rcvEmailsAdapter: RcvEmailsAdapter

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {/*
          val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            // Swap items in your data list
            Collections.swap(viewModel.emailsList.value!!, fromPosition, toPosition)

            // Notify adapter of the move
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)


           return true
             */
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                Toast.makeText(this@MainActivity, "Swiped Left", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Swiped Right", Toast.LENGTH_SHORT).show()

                val emailIdToDelete = rcvEmailsAdapter.getItem(viewHolder.adapterPosition)
                if (emailIdToDelete!=null){
                    viewModel.removeEmailWithId(emailIdToDelete)
                }

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = EmailsListViewModel()

        binding.apply {

            setSupportActionBar(toolbar)

            viewModel.emailsList.observe(
                this@MainActivity, Observer {
                    rcvEmailsAdapter = RcvEmailsAdapter(it)

                    rcvEmails.adapter = rcvEmailsAdapter

                    rcvEmails.layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

                    ItemTouchHelper(swipeCallback).attachToRecyclerView(rcvEmails)

                })

        }

    }
}