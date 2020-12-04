package com.example.carrental.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RentingDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rent: Renting)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param rent new value to write
     */
    @Update
    suspend fun update(rent: Renting)


    @Query("SELECT * from cars WHERE id = :key")
    suspend fun getCar(key: Long): Cars?

    @Update
    suspend fun updateCar(car: Cars)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from renting WHERE id = :key")
    suspend fun get(key: Long): Renting?

    @Query("SELECT * from customers WHERE id = :key")
    suspend fun getCustomer(key: Long): Customers?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM renting")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM renting ORDER BY id DESC")
    fun getAllRenting(): LiveData<List<Renting>>

    /**
     * Selects and returns the latest rent.
     */
    @Query("SELECT * FROM renting ORDER BY id DESC LIMIT 1")
    suspend fun getToRenting(): Renting?

    /**
     * Selects and returns the rent with given rentId.
     */
//    @Query("SELECT * from renting WHERE Id = :key")
//    suspend fun getRentWithId(key: Long): LiveData<Renting>
//
//
//
//    @Transaction
//    @Query("SELECT * FROM customers WHERE id IN (SELECT DISTINCT(customer_id) FROM renting)")
//    suspend fun getRentingWithCustomers(): LiveData<List<CustomerWithRent>>

    @Query("SELECT SUM(price) FROM renting")
    suspend fun getIncome(): Long?

    @Query("SELECT SUM(price) FROM services")
    suspend fun getOutcome(): Long?


}