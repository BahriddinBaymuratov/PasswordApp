package com.example.passwordapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun dao(): PasswordDao

    companion object {
        @Volatile
        private var instance: PasswordDatabase? = null
        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "password.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}