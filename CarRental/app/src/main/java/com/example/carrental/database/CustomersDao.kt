package com.example.carrental.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomersDao{
    @Insert
    suspend fun insert(customer: Customers)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param customer new value to write
     */
    @Update
    suspend fun update(customer: Customers)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from customers WHERE id = :key")
    suspend fun get(key: Long): Customers?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM customers")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM customers ORDER BY id DESC")
    fun getAllCustomers(): LiveData<List<Customers>>

    /**
     * Selects and returns the latest customer.
     */
    @Query("SELECT * FROM customers ORDER BY id DESC LIMIT 1")
    suspend fun getToCustomer(): Customers?

    /**
     * Selects and returns the customer with given customerId.
     */
    @Query("SELECT * from customers WHERE Id = :key")
    fun getCustomersWithId(key: Long): LiveData<Customers>

    @Transaction
    @Query("SELECT cars.name, renting.price, renting.startDate, renting.endDate,renting.id FROM cars,renting WHERE cars.id = car_id AND renting.customer_id = :key ORDER BY renting.endDate DESC ")
    fun getRentingWithCars(key: Long): LiveData<List<CarsWithRent>>
    
}