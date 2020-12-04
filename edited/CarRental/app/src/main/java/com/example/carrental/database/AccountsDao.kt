package com.example.accountrental.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.carrental.database.Accounts

@Dao
interface AccountsDao{
    @Insert
    suspend fun insert(account: Accounts)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param account new value to write
     */
    @Update
    suspend fun update(account: Accounts)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from accounts WHERE id = :key")
    suspend fun get(key: Long): Accounts?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM accounts")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM accounts ORDER BY id DESC")
    fun getAllAccounts(): LiveData<List<Accounts>>

    /**
     * Selects and returns the latest account.
     */
    @Query("SELECT * FROM accounts ORDER BY id DESC LIMIT 1")
    suspend fun getToCar(): Accounts?

    /**
     * Selects and returns the account with given accountId.
     */
    @Query("SELECT * from accounts WHERE Id = :key")
    fun getCarWithId(key: Long): LiveData<Accounts>
}