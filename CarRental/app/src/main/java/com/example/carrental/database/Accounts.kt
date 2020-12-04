package com.example.carrental.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Accounts(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val accountId: Int?,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    val password: String?,

    val type: String?
)