package com.example.carrental.database

import androidx.room.*

@Entity(
    tableName = "services", foreignKeys = [
        ForeignKey(
            entity = Cars::class,
            parentColumns = ["id"],
            childColumns = ["car_id"]

        )], indices = [Index(value = ["car_id"])]
)
data class Services(
    val serviceType: String?,
    val price: Int?,
    val date: Long? = System.currentTimeMillis(),
    @ColumnInfo(name = "car_id")
    val carId: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {

}