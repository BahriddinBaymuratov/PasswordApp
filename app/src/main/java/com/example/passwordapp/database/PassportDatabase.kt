package com.example.passwordapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class PassportDatabase: RoomDatabase() {
    abstract fun dao(): PassportDao

    companion object{
        @Volatile
        private var instance: PassportDatabase? =null
        operator fun invoke(context: Context) = instance ?: synchronized(Any()){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            PassportDatabase::class.java,
            "passport.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}