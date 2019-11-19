package com.viseo.poc.myweatherapplication.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CityTest {


    private lateinit var city: City

    @Before
    fun setUp() {
        city = City("City1", 0.1, 0.1, false)
    }

    @Test
    fun defaultValues_isValid() {
        val defaultPlant = city
        Assert.assertEquals(0, defaultPlant.cityId)
        Assert.assertNull(defaultPlant.weather)
    }

    @Test
    fun toString_isValid() {
        Assert.assertEquals("City1", city.toString())
    }
}