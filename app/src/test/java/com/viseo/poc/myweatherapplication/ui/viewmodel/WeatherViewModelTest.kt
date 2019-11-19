package com.viseo.poc.myweatherapplication.ui.viewmodel

import android.app.Application
import com.viseo.poc.myweatherapplication.data.City
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WeatherViewModelTest {
    @Mock
    private val mockApplication: Application? = null
    private lateinit var weatherViewModel: WeatherViewModel

    //History List with Duplications
    private val historyDuplications = arrayOf(
        City("City1", 0.1, 0.1, true),
        City("City2", 0.1, 0.1, true),
        City("City3", 0.1, 0.1, true),
        City("City2", 0.1, 0.1, true),
        City("City1", 0.1, 0.1, true)
    ).asList()

    private val historyMoreThan10 = arrayOf(
        City("City1", 0.1, 0.1, true),
        City("City2", 0.1, 0.1, true),
        City("City3", 0.1, 0.1, true),
        City("City4", 0.1, 0.1, true),
        City("City5", 0.1, 0.1, true),
        City("City6", 0.1, 0.1, true),
        City("City7", 0.1, 0.1, true),
        City("City8", 0.1, 0.1, true),
        City("City9", 0.1, 0.1, true),
        City("City10", 0.1, 0.1, true),
        City("City11", 0.1, 0.1, true)
    ).asList()


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
            "Invalid special character &",
            weatherViewModel.isValidCityName("New York & Washington")
        )
        assertFalse(
            "Invalid special character =",
            weatherViewModel.isValidCityName("New York = Washington")
        )
        assertFalse("Empty String", weatherViewModel.isValidCityName(""))
    }

    @Test
    fun getCityHistory() {
        assertEquals(
            "Filtered list: Remove duplications",
            3,
            weatherViewModel.filterCityHistory(historyDuplications).size
        )
        assertEquals(
            "Filtered list: Max 10 items",
            10,
            weatherViewModel.filterCityHistory(historyMoreThan10).size
        )
    }

}