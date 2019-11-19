package com.viseo.poc.myweatherapplication.ui.viewmodel

import android.app.Application
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WeatherViewModelTest {
    @Mock
    private val mockApplication: Application? = null
    private lateinit var weatherViewModel: WeatherViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = mockApplication?.let { WeatherViewModel(it) }!!
    }

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