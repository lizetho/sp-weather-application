package com.viseo.poc.myweatherapplication.ui.home

import androidx.lifecycle.ViewModel

class HomePageViewModel : ViewModel() {
    // List of cities
    val cityList: List<String> = emptyList()

    fun searchCity(s: CharSequence) {
        // TODO Search city using API
    }
}
