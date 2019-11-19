package com.viseo.poc.myweatherapplication.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.viseo.poc.myweatherapplication.data.AppDatabase
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.data.CityRepository
import com.viseo.poc.myweatherapplication.data.DataManager
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CityRepository
    val cityHistory: LiveData<List<City>>

    init {
        // Gets reference to Dao to build the Repository.
        val cityDao = AppDatabase.getDatabase(application, viewModelScope).cityDao()
        repository = CityRepository(cityDao)
        cityHistory = repository.cityHistory
    }


    fun insertCityOnDB(city: City) = viewModelScope.launch {
        repository.insert(city)
    }


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

    fun searchCityWeather(
        context: Context,
        city: City,
        completionHandler: (response: City?, error: String?) -> Unit
    ) {
        DataManager(context).getCityWeather(city, completionHandler)
    }

    @VisibleForTesting
    fun isValidCityName(cityName: String): Boolean {
        // TODO handle case of string with special characters.
        return cityName.isNotEmpty() && cityName.matches(Regex("^[a-zA-Z .-]*$"))
    }
}
