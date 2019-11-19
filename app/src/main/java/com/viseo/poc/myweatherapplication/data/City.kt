package com.viseo.poc.myweatherapplication.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    var name: String?,
    var latitude: Double?,
    var longitude: Double?,
    var inHistory: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0
    @Ignore
    var weather: CityWeather? = null


}