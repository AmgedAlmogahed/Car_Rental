package com.example.carrental.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customers(
    val name: String? = "",
    @ColumnInfo(name = "passport_number")
    val passportNumber: String? = "",

    val passport: Int? = 0,

    val license: Int? = 0,

    val customerImage: Byte = 0,

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val customerId: Long = 0L
)
