package com.viseo.poc.myweatherapplication.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.viseo.poc.myweatherapplication.R
import com.viseo.poc.myweatherapplication.data.City
import kotlinx.android.synthetic.main.home_page_fragment.*

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: HomePageViewModel
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
        viewModel = ViewModelProviders.of(this).get(HomePageViewModel::class.java)
    }

    // --------------------
    // Business logic bloc
    // --------------------
    private fun initView() {
        // Init recycler view
        context?.let {
            cityAdapter = CityAdapter(it)
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

    fun populateCityList(cities: MutableList<City>) {
        cityAdapter.setCities(cities)
    }
}
