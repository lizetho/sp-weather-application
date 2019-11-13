package com.viseo.poc.myweatherapplication

import android.app.Application
import com.viseo.poc.myweatherapplication.service.VolleyService

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VolleyService.initialize(this)
    }
}