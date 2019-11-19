package com.viseo.poc.myweatherapplication.data

data class CityWeather(
    val temperature: Int,
    val weatherIconUrl: String,
    val weatherDesc: String,
    val humidity: Double
)