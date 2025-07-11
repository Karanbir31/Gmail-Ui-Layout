package com.example.gmailuilayout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rcvEmails: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        rcvEmails = findViewById(R.id.rcvEmails)

        rcvEmails.adapter = RcvEmailsAdapter()
        rcvEmails.layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



    }
}