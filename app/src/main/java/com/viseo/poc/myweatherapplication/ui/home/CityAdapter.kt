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
    context: Context, private val clickListener: (City) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val CITY_ITEM_VIEW_TYPE = 0
        const val SECTION_VIEW_TYPE = 1
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listData = CityListData("", emptyList())

    data class CityListData(val sectionTitle: String, val cities: List<City>)

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityItemView: TextView = itemView.findViewById(R.id.cityNameTextView)
        fun bind(city: City, clickListener: (City) -> Unit) {
            cityItemView.text = city.name
            itemView.setOnClickListener { clickListener(city) }
        }
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CITY_ITEM_VIEW_TYPE) {
            CityViewHolder(inflater.inflate(R.layout.city_item, parent, false))
        } else {
            SectionViewHolder(inflater.inflate(R.layout.city_section, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CITY_ITEM_VIEW_TYPE -> (holder as CityViewHolder).bind(
                listData.cities[position - 1], //Note that position 0 is for section header
                clickListener
            )
            SECTION_VIEW_TYPE -> (holder as SectionViewHolder).sectionTextView.text =
                listData.sectionTitle
        }

    }

    fun setCities(sectionTitle: String, cities: List<City>) {
        this.listData = CityListData(sectionTitle, cities = cities)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return SECTION_VIEW_TYPE
        } else {
            return super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int = if (listData.cities.isNotEmpty()) {
        listData.cities.size + 1 // Adding section item
    } else {
        0
    }

}