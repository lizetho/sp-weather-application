package com.viseo.poc.myweatherapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //Daos
    abstract fun cityDao(): CityDao

    companion object {
        private const val DATABASE_NAME = "weather-db"

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context, scope)
                INSTANCE = instance
                // return instance
                instance
            }

        private fun buildDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

}