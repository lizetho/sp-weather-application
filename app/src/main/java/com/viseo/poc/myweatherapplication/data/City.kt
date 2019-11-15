package com.viseo.poc.myweatherapplication.data

data class City(
    val cityId: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    var weather: CityWeather?
) {
}