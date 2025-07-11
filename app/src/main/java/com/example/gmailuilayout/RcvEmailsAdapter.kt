package com.example.gmailuilayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvEmailsAdapter(
    private val emails: List<String> = emptyList()
) : RecyclerView.Adapter<RcvEmailsAdapter.RcvEmailsViewHolder>() {

    inner class RcvEmailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userImage : ImageView = view.findViewById(R.id.rcv_user_image)
        private val userName : TextView = view.findViewById(R.id.rcv_user_name)
        private val emailTime : TextView = view.findViewById(R.id.rcv_email_time)
        private val emailSubject : TextView = view.findViewById(R.id.rcv_email_subject)
        private val emailMessage : TextView = view.findViewById(R.id.rcv_email_message)
        private val starImageButton : ImageButton = view.findViewById(R.id.starImageButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvEmailsViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(
           R.layout.rcv_email_list_item, parent, false
       )

        return RcvEmailsViewHolder(view)
    }

    override fun getItemCount() = 10


    override fun onBindViewHolder(holder: RcvEmailsViewHolder, position: Int) {

    }


}