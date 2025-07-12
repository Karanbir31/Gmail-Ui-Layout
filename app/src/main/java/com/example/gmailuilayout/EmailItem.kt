package com.example.gmailuilayout

import java.time.LocalDateTime

data class EmailItem(
    val emailId : Int = 0,
    val senderImage : String = "",
    val senderName : String = "",
    val emailDateTime : LocalDateTime = LocalDateTime.now(),
    val emailSubject : String = "",
    val emailMessage : String = "",
    var starMarked : Boolean =  false

)
