package com.viseo.poc.myweatherapplication.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.viseo.poc.myweatherapplication.R
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.ui.viewmodel.WeatherViewModel
import com.viseo.poc.myweatherapplication.utils.removeFragment
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {
    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        viewModel = ViewModelProviders.of(activity!!).get(WeatherViewModel::class.java)
        viewModel.selectedCity.observe(this,
            Observer<City> { city ->
                println("Got the city in the second fragment $city")
                cityWeatherCityNameText.text = city!!.name
                cityWeatherProgressBar.show()
                context?.let {
                    viewModel.searchCityWeather(
                        it,
                        city
                    ) { responseCity: City?, error: String? ->
                        cityWeatherProgressBar.hide()
                        if (error != null) {
                            //TODO notify there is some error and come back to previous page

                        } else {
                            // Fill view
                            cityWeatherHumidityText.text = getString(
                                R.string.city_weather_humidity,
                                responseCity?.weather?.humidity.toString()
                            )
                            cityWeatherTemperatureText.text = getString(
                                R.string.city_weather_temperature,
                                responseCity?.weather?.temperature.toString()
                            )
                            cityWeatherDescriptionText.text = responseCity?.weather?.weatherDesc

                            // TODO implement the image loader
                            //                                responseCity?.weather?.weatherIconUrl.let {
                            //                                    cityWeatherImage.setImageUrl(
                            //                                        responseCity?.weather?.weatherIconUrl,
                            //                                        VolleyService.getInstance(context!!).imageLoader
                            //                                    )
                            //                                }
                        }
                    }
                }
            })
    }

    private fun initToolbar() {
        cityWeatherToolbar.setNavigationIcon(R.drawable.keyboard_arrow_left)
        cityWeatherToolbar.setNavigationOnClickListener {
            activity?.removeFragment(this)
        }
    }

}
