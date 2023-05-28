package com.example.passwordapp.database

import androidx.room.*

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePassword(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePassword(user: User)

    @Delete
    fun deletePassword(user: User)

    @Query("SELECT * FROM PasswordTable ORDER BY id DESC")
    fun getAllPassword(): List<User>

    @Query("DELETE FROM PasswordTable")
    fun deleteAllPassword()

}