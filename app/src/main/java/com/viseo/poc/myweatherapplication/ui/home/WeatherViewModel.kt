package com.viseo.poc.myweatherapplication.ui.home

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.data.DataManager

class WeatherViewModel : ViewModel() {

    private val _selectedCity = MutableLiveData<City>()
    val selectedCity: LiveData<City>
        get() = _selectedCity

    fun setSelectedCity(city: City) {
        _selectedCity.postValue(city)
    }

    fun searchCity(
        context: Context,
        cityName: String,
        completionHandler: (response: MutableList<City>) -> Unit
    ) {
        if (isValidCityName(cityName)) {
            DataManager(context).getCities(cityName, completionHandler)
        }
    }

    @VisibleForTesting
    fun isValidCityName(cityName: String): Boolean {
        // TODO handle case of string with special characters.
        return cityName.isNotEmpty() && cityName.matches(Regex("^[a-zA-Z .-]*$"))
    }
}
