package com.example.carrental.database


import androidx.room.*


@Entity(
    tableName = "renting", foreignKeys = [
        ForeignKey(
            entity = Cars::class,
            parentColumns = ["id"],
            childColumns = ["car_id"]
        ),
        ForeignKey(
            entity = Customers::class,
            parentColumns = ["id"],
            childColumns = ["customer_id"]
        )], indices = [Index(value = ["car_id"]),Index(value = ["customer_id"])]
)
data class Renting(
    val moveAgent: String? = "",
    var price: Long? = 0L,
    val startDate: Long = System.currentTimeMillis(),
    var endDate: Long? = -1,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "car_id")
    val carId: Long = 0L,
    @ColumnInfo(name = "customer_id")
    val customerId: Long = 0L
)

