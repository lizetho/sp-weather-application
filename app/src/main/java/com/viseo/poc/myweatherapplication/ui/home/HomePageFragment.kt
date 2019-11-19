package com.viseo.poc.myweatherapplication.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.viseo.poc.myweatherapplication.R
import com.viseo.poc.myweatherapplication.data.City
import com.viseo.poc.myweatherapplication.ui.viewmodel.WeatherViewModel
import com.viseo.poc.myweatherapplication.ui.weather.WeatherFragment
import com.viseo.poc.myweatherapplication.utils.addFragment
import kotlinx.android.synthetic.main.home_page_fragment.*

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var cityAdapter: CityAdapter

    // --------------------
    // Lifecycle Bloc
    // --------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(WeatherViewModel::class.java)
        populateCityHistory()
    }

    // --------------------
    // Business logic bloc
    // --------------------

    private fun initView() {
        // Init recycler view
        context?.let {
            cityAdapter = CityAdapter(it) { cityItem: City -> onClickCityItem(cityItem) }
            cityResultRecyclerView.adapter = cityAdapter
            linearLayoutManager = LinearLayoutManager(context)
            cityResultRecyclerView.layoutManager = linearLayoutManager
        }


        // Init listeners for EditText to send the text to API
        citySearchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                word: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                context?.let {
                    viewModel.searchCity(it, word.toString()) {
                        populateCityList(it)
                    }
                }
            }
        })
    }


    private fun populateCityHistory() {
        viewModel.cityHistory.observe(viewLifecycleOwner, Observer { cities ->
            cities?.let {
                cityAdapter.setCities(
                    getString(R.string.homepage_history_title),
                    cities
                )
            }
        })

    }

    private fun onClickCityItem(cityItem: City) {
        viewModel.setSelectedCity(cityItem)
        viewModel.insertCityOnDB(cityItem)
        activity?.addFragment(WeatherFragment(), R.id.container)
    }

    fun populateCityList(cities: MutableList<City>) {
        cityAdapter.setCities(getString(R.string.homepage_results_title), cities)
    }
}
