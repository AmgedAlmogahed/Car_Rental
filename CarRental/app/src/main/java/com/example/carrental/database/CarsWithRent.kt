package com.example.carrental.database

import androidx.room.Embedded
import androidx.room.Relation

data class CarsWithRent(
   val id: Long?,
   val name: String?,
   val price : String?,
   val startDate : Long?,
   val endDate: Long?
)

//@Embedded
//val cars: Cars,
//@Relation(
//    parentColumn = "id",
//    entityColumn = "car_id"
//)
//val renting: List<Renting>