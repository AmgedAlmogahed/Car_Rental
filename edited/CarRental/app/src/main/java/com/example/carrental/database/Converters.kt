package com.example.carrental.database

import androidx.room.TypeConverter
import java.sql.Date


object Converters {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time?.toLong()
    }
}