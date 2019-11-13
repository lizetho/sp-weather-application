package com.viseo.poc.myweatherapplication.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.data.DataManager

class HomePageViewModel : ViewModel() {
    // List of cities
    val cityList: List<String> = emptyList()


    fun searchCity(
        context: Context,
        cityName: String,
        completionHandler: (response: MutableList<City>) -> Unit
    ) {
        // TODO handle case of empty string, clean string, avoid special characters.
        // Handle repetitions
        DataManager(context).getCities(cityName, completionHandler)
    }
}
