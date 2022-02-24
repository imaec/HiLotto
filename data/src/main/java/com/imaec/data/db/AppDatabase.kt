package com.imaec.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imaec.data.db.dao.NumberDao
import com.imaec.data.entity.NumberEntity

@Database(
    entities = [
        NumberEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun numberDao(): NumberDao

    companion object {
        private const val DB_NAME = "lotto-database"

        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
