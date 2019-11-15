package com.viseo.poc.myweatherapplication.ui.viewmodel

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherViewModelTest {
    private val weatherViewModel =
        WeatherViewModel()

    @Test
    fun isValidCityName_isValid() {
        assertTrue(weatherViewModel.isValidCityName("New York"))
        assertTrue(weatherViewModel.isValidCityName("Bog"))
        assertTrue(weatherViewModel.isValidCityName("Bogota D. C."))
        assertTrue(weatherViewModel.isValidCityName("Saint-Malo"))
    }

    @Test
    fun isValidCityName_isInvalid() {
        assertFalse(
            "Invalid special characters",
            weatherViewModel.isValidCityName("New York & Washington(*&%")
        )
        assertFalse("Empty String", weatherViewModel.isValidCityName(""))
    }
}