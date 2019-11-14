package com.viseo.poc.myweatherapplication.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viseo.poc.myweatherapplication.R
import com.viseo.poc.myweatherapplication.data.City

/**
 * Adapter for the [RecyclerView] in [HomePageFragment].
 */
class CityAdapter internal constructor(
    context: Context, val clickListener: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cities = emptyList<City>()

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityItemView: TextView = itemView.findViewById(R.id.cityNameTextView)
        fun bind(city: City, clickListener: (City) -> Unit) {
            cityItemView.text = city.name
            itemView.setOnClickListener { clickListener(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = inflater.inflate(R.layout.city_item, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cities[position], clickListener)
    }

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun getItemCount() = cities.size

}