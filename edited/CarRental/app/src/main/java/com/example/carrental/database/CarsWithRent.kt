package com.example.carrental.database



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