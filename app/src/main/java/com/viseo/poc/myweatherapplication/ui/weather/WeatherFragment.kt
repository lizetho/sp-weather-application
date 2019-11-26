package com.viseo.poc.myweatherapplication.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.ImageLoader
import com.viseo.poc.myweatherapplication.R
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.service.VolleyService
import com.viseo.poc.myweatherapplication.ui.viewmodel.WeatherViewModel
import com.viseo.poc.myweatherapplication.utils.BitmapLruCache
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
        viewModel = ViewModelProvider(activity!!).get(WeatherViewModel::class.java)
        viewModel.selectedCity.observe(viewLifecycleOwner,
            Observer<City> { city ->
                cityWeatherCityNameText.text = city!!.name
                cityWeatherProgressBar.show()
                context?.let {
                    viewModel.searchCityWeather(
                        it,
                        city
                    ) { responseCity: City?, error: String? ->
                        cityWeatherProgressBar.hide()
                        if (error != null) {
                            //Notify there is some error and come back to previous page
                            displayErrorDialog(it)
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
                            responseCity?.weather?.weatherIconUrl.let {
                                cityWeatherImage.visibility = View.VISIBLE
                                cityWeatherImage.setImageUrl(
                                    it,
                                    ImageLoader(
                                        VolleyService.getInstance(context!!).requestQueue,
                                        BitmapLruCache()
                                    )
                                )
                            }

                        }
                    }
                }
            })
    }

    private fun initToolbar() {
        cityWeatherToolbar.setNavigationIcon(R.drawable.keyboard_arrow_left)
        cityWeatherToolbar.setNavigationOnClickListener {
            goToMainPage()
        }
    }

    private fun displayErrorDialog(it: Context) {
        val dialogBuilder = AlertDialog.Builder(it)
        // set message of alert dialog
        dialogBuilder.setMessage(R.string.error_message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                goToMainPage()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setTitle(R.string.error_message_title)
        alert.show()
    }


    private fun goToMainPage() {
        activity?.removeFragment(this)
    }

}
