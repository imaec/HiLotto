package com.imaec.hilotto.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity

@Database(entities = [
    NumberEntity::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun numberDao(): NumberDao

    companion object {
        private const val DB_NAME = "room-db"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    // Created Database
                }).build()
        }
    }
}