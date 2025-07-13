package com.example.gmailuilayout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gmailuilayout.databinding.RcvEmailListItemBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RcvEmailsAdapter(
    private val emails: List<EmailItem>
) : RecyclerView.Adapter<RcvEmailsAdapter.RcvEmailsViewHolder>() {

    private lateinit var context: Context

    inner class RcvEmailsViewHolder(val rcvBinding: RcvEmailListItemBinding) :
        RecyclerView.ViewHolder(rcvBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvEmailsViewHolder {
        context = parent.context

        val rcvDataBinding =
            RcvEmailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RcvEmailsViewHolder(rcvDataBinding)
    }

    override fun getItemCount() = 10


    override fun onBindViewHolder(holder: RcvEmailsViewHolder, position: Int) {
        val currEmail = emails[position]

        val dateOrTime: String = if (currEmail.emailDateTime.toLocalDate().equals(LocalDate.now())) {
            currEmail.emailDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM"))
        } else {
            currEmail.emailDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        val starIcon = if (currEmail.starMarked) {
            R.drawable.star_button_selected
        }else{
            R.drawable.star_button_unselected
        }


        holder.rcvBinding.apply {

            Glide.with(context).load(currEmail.senderImage).into(rcvSenderImage)

            rcvSenderName.text = currEmail.senderName
            rcvEmailDateTime.text = dateOrTime

            rcvEmailSubject.text = currEmail.emailSubject
            rcvEmailMessage.text = currEmail.emailMessage

            rcvStarMarkedImageButton.setImageResource(starIcon)

            rcvStarMarkedImageButton.setOnClickListener{
                currEmail.starMarked = !currEmail.starMarked
            }
        }
    }

    fun getItem(position: Int): Int? =
        if (position in emails.indices) emails[position].emailId else null



}