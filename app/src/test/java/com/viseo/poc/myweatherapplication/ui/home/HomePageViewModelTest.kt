package com.viseo.poc.myweatherapplication.ui.home

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomePageViewModelTest {
    val homePageViewModel = HomePageViewModel()

    @Test
    fun isValidCityName_isValid() {
        assertTrue(homePageViewModel.isValidCityName("New York"))
        assertTrue(homePageViewModel.isValidCityName("Bog"))
        assertTrue(homePageViewModel.isValidCityName("Bogota D. C."))
        assertTrue(homePageViewModel.isValidCityName("Saint-Malo"))
    }

    @Test
    fun isValidCityName_isInvalid() {
        assertFalse(
            "Invalid special characters",
            homePageViewModel.isValidCityName("New York & Washington(*&%")
        )
        assertFalse("Empty String", homePageViewModel.isValidCityName(""))
    }
}