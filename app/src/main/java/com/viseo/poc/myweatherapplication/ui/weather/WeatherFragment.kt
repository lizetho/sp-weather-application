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
import com.viseo.poc.myweatherapplication.ui.home.WeatherViewModel
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
            object : Observer<City> {
                override fun onChanged(city: City?) {
                    println("Got the city in the second fragment $city")
                    cityWeatherCityNameText.text = city!!.name
                }
            })

    }

    private fun initToolbar() {
        cityWeatherToolbar.setNavigationIcon(R.drawable.keyboard_arrow_left)
        cityWeatherToolbar.setNavigationOnClickListener({
            activity?.removeFragment(this)
        })
    }

}
