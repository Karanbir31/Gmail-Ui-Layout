package com.example.gmailuilayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmailuilayout.databinding.FragmentMailsBinding


class MailsFragment : Fragment() {

    private lateinit var binding: FragmentMailsBinding
    private lateinit var rcvEmailsAdapter: RcvEmailsAdapter
    private lateinit var emailsList: MutableList<EmailItem>
    private lateinit var viewModel: EmailsListViewModel
    private  var deleteIcon : Drawable? = null


    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {

        private val background = ColorDrawable(Color.RED) // ✅ Declare it here
        private val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
        private val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0

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

                //emailsListViewModel.removeEmailWithId(position)
                emailsList.remove(emailsList[position])
                rcvEmailsAdapter.notifyItemRemoved(position)
                rcvEmailsAdapter.notifyItemRangeChanged(position, emailsList.size)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
            return 0.7f
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            val itemView = viewHolder.itemView
            val itemHeight = itemView.bottom - itemView.height

            if (dX>0){
                // means right swipe -----> so draw left to right
                background.setBounds(itemView.left,  itemView.top,itemView.left + dX.toInt(),  itemView.bottom)
            }else{
                // dX < 0 | so swipe left <------ so draw right to left
                background.setBounds(itemView.right + dX.toInt(),  itemView.top,itemView.right,  itemView.bottom)
            }
            background.draw(c)

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emailsList = mutableListOf()
        viewModel = EmailsListViewModel()
        emailsList.addAll(viewModel.dummyData())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentMailsBinding.inflate(inflater, container, false)
        rcvEmailsAdapter = RcvEmailsAdapter(emailsList)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_24)
            ItemTouchHelper(swipeCallback).attachToRecyclerView(rcvEmails)
            rcvEmails.adapter = rcvEmailsAdapter
            rcvEmails.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcvEmails.adapter = rcvEmailsAdapter
            rcvEmails.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


            fabButtonCompose.setOnClickListener {
                val email = viewModel.getRandomEmail()
                emailsList.add(0, email) // Add to top
                rcvEmailsAdapter.notifyItemInserted(0)
                binding.rcvEmails.scrollToPosition(0) // Optional: scroll to new item
                Toast.makeText(requireContext(), "${email.senderName} Added", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

}