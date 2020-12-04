package com.example.carrental.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServicesDao{
    @Insert
    suspend fun insert(service: Services)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param service new value to write
     */
    @Update
    suspend fun update(service: Services)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from services WHERE id = :key")
    suspend fun get(key: Long): Services?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM services")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM services ORDER BY id DESC")
    fun getAllServices(): LiveData<List<Services>>

    /**
     * Selects and returns the latest service.
     */
    @Query("SELECT * FROM services ORDER BY id DESC LIMIT 1")
    suspend fun getToService(): Services?

    /**
     * Selects and returns the service with given serviceId.
     */
    @Query("SELECT * from services WHERE Id = :key")
    fun getServiceWithId(key: Long): LiveData<Services>
}