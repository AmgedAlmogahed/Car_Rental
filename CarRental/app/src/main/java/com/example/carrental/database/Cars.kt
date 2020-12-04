package com.example.carrental.database

import androidx.room.*
import com.example.carrental.ui.AVAILABLE

@Entity(tableName = "cars")
data class Cars(
    var name: String? = "",

    @ColumnInfo(name = "plat_number")
    var platNumber: String? = "",

    @ColumnInfo(name = "base_number")
    var baseNumber: String? = "",

    var model: String? = "",

    var type: String? = "",

    @ColumnInfo(name = "rent_per_hour")
    var rentPerHour: Long? = 0L,

    var color: String? = "",

    var status: String? = AVAILABLE,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val carId: Long = 0L
)


