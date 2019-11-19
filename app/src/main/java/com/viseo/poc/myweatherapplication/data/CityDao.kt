package com.viseo.poc.myweatherapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    /**
     * According to UC #4 Get the last items
     */
    @Query("SELECT * from city ORDER BY cityId desc")
    fun getCityHistory(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("DELETE FROM city")
    suspend fun deleteAll()
}