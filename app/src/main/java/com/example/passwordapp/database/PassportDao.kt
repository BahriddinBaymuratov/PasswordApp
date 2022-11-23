package com.example.passwordapp.database

import androidx.room.*
import androidx.room.Entity

@Dao
interface PassportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePassport(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePassport(user: User)

    @Delete
    fun deletePassport(user: User)

    @Query("SELECT * FROM PassportTable ORDER BY id DESC")
    fun getAllPassport(): List<User>

    @Query("DELETE FROM PassportTable")
    fun deleteAllPassport()
}