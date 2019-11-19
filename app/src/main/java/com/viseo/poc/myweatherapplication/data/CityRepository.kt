package com.viseo.poc.myweatherapplication.data

import androidx.lifecycle.LiveData

class CityRepository(private val cityDao: CityDao) {

    val cityHistory: LiveData<List<City>> = cityDao.getCityHistory()

    // This insertion is going to be called for a coroutine
    suspend fun insert(city: City) {
        cityDao.insert(city)
    }
}