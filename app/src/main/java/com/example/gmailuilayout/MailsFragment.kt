package com.example.gmailuilayout

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
import com.bumptech.glide.Glide
import com.example.gmailuilayout.databinding.FragmentMailsBinding

class MailsFragment : Fragment() {

    private lateinit var binding: FragmentMailsBinding
    private lateinit var rcvEmailsAdapter: RcvEmailsAdapter
    private lateinit var emailsList: MutableList<EmailItem>
    private lateinit var viewModel: EmailsListViewModel
    private var deleteIcon: Drawable? = null

    private var searchQuery = ""


    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {

        private val background = ColorDrawable(Color.parseColor("#F44336")) // ✅ Declare it here

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

            val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
            val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0

            val itemView = viewHolder.itemView
            val itemHeight = itemView.bottom - itemView.top


            val iconMargin = (itemHeight - intrinsicHeight) / 2
            val iconTop = itemView.top + iconMargin
            val iconBottom = iconTop + intrinsicHeight
            val iconLeft: Int
            val iconRight: Int

            if (dX > 0) {
                // means right swipe -----> so draw left to right
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt(),
                    itemView.bottom
                )

                iconLeft = itemView.left + iconMargin
                iconRight = iconLeft + intrinsicWidth

            } else if (dX < 0) {
                // dX < 0 | so swipe left <------ so draw right to left
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )

                iconRight = itemView.right - iconMargin
                iconLeft = iconRight - intrinsicWidth
            } else {
                iconLeft = 0
                iconRight = 0
            }
            background.draw(c)

            if (deleteIcon != null) {
                deleteIcon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                deleteIcon!!.draw(c)
            }

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

            searchBar.setNavigationIcon(R.drawable.outline_menu_24) // Replace with your own icon
            Glide.with(requireContext()).load("https://randomuser.me/api/portraits/women/9.jpg")
                .into(rcvUsersImage)

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

            searchBar.setOnClickListener {
                searchView.show()
            }

            searchView.editText.setOnEditorActionListener { textView, actionId, event ->

                searchQuery = searchView.text.toString()
                searchBar.setText(searchQuery)

                // perform task on query like | api calls | list filter | etc

                searchView.hide()

                Toast.makeText(requireContext(), "searchQuery is $searchQuery", Toast.LENGTH_SHORT)
                    .show()

                true
            }
        }
    }
}