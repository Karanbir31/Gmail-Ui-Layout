package com.example.gmailuilayout

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeConvertor {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm dd MMM")

    @Ty
    fun fromLocalDateTime(localDateTime: LocalDateTime) : String{
       return localDateTime.format(formatter)
    }

    fun toLocalDateTime(localDateTimeStr: String) : LocalDateTime{
       return LocalDateTime.parse(localDateTimeStr, formatter)
    }

}