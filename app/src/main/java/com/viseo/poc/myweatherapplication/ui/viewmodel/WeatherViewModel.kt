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
    private val _selectedCity = MutableLiveData<City>()
    val cityHistory: LiveData<List<City>>
    val selectedCity: LiveData<City>
        get() = _selectedCity

    fun setSelectedCity(city: City) {
        _selectedCity.postValue(city)
    }

    init {
        // Gets reference to Dao to build the Repository.
        val cityDao = AppDatabase.getDatabase(application, viewModelScope).cityDao()
        repository = CityRepository(cityDao)
        cityHistory = repository.cityHistory
    }

    fun insertCityOnDB(city: City) = viewModelScope.launch {
        repository.insert(city)
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

    // business logic bloc:

    @VisibleForTesting
    fun isValidCityName(cityName: String): Boolean {
        return cityName.isNotEmpty() && cityName.matches(Regex("^[a-zA-Z .-]*$"))
    }


    /**
     * Return the filtered list of Cities without duplication and only the last 10.
     */
    fun filterCityHistory(cities: List<City>): List<City> {
        var cityHistory = cities.distinctBy { it.name }
        if (cityHistory.size > 10) {
            cityHistory = cityHistory.subList(0, 10)
        }
        return cityHistory
    }
}
