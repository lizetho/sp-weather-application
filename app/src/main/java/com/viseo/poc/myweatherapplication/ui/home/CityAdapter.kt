package com.viseo.poc.myweatherapplication.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viseo.poc.myweatherapplication.R

/**
 * Adapter for the [RecyclerView] in [HomePageFragment].
 */
class CityAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cities = emptyList<String>()

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityItemView: TextView = itemView.findViewById(R.id.cityNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = inflater.inflate(R.layout.city_item, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val current = cities[position]
        holder.cityItemView.text = current
    }

    fun setCities(cities: List<String>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun getItemCount() = cities.size

}