package com.example.gmailuilayout

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeConvertor {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime) : String{
       return localDateTime.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(localDateTimeStr: String) : LocalDateTime{
       return LocalDateTime.parse(localDateTimeStr, formatter)
    }

}