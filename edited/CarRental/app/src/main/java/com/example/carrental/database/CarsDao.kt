package com.example.carrental.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarsDao{
    @Insert
    suspend fun insert(car: Cars)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param car new value to write
     */
    @Update
    suspend fun update(car: Cars)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from cars WHERE id = :key")
    suspend fun get(key: Long): Cars?

    @Query("SELECT * from renting WHERE car_id IN(SELECT DISTINCT(id) FROM cars WHERE id = :key) AND endDate = -1 ")
    suspend fun getRent(key: Long): Renting?

    @Update
    suspend fun updateRent(rent: Renting)

    @Query("SELECT * from cars ")
    suspend fun getCars(): List<Cars>



    @Query("SELECT SUM(price) FROM renting WHERE car_id = :key")
    suspend fun getCarIncome(key : Long) :Long?

    @Query("SELECT SUM(price) FROM services WHERE car_id = :key")
    suspend fun getCarOutcome(key: Long): Long?
    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM cars")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM cars ORDER BY id DESC")
    fun getAllCars(): LiveData<List<Cars>>

    /**
     * Selects and returns the latest car.
     */
    @Query("SELECT * FROM cars ORDER BY id DESC LIMIT 1")
    suspend fun getToCar(): Cars?

    /**
     * Selects and returns the car with given carId.
     */
    @Query("SELECT * from cars WHERE Id = :key")
    fun getCarWithId(key: Long): LiveData<Cars>


    @Transaction
    @Query("SELECT customers.name, renting.price, renting.startDate, renting.endDate,renting.id FROM customers,renting WHERE customers.id = customer_id AND car_id = :key ")
    fun getRentingWithCustomers(key: Long): LiveData<List<CustomerWithRent>>




//    @Query("DELETE FROM cars WHERE id = :key")
//    fun deleteCar(key : Long): Cars

//    @Transaction
//    @Query("SELECT * FROM cars")
//    fun getCarsWithRentAndCustomer(): List<CarsWithRentsAndCustomers>
}