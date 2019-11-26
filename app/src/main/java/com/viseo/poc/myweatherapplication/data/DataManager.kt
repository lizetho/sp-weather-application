package com.viseo.poc.myweatherapplication.data

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.viseo.poc.myweatherapplication.service.VolleyService
import org.json.JSONException
import org.json.JSONObject

class DataManager(context: Context) {
    private var context: Context = context
    private val cityUrl =
        "https://api.worldweatheronline.com/premium/v1/search.ashx?key=73d5fed1b60c4cf6925100740190711&format=json&query="
    private val weatherUrl =
        "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=73d5fed1b60c4cf6925100740190711&date=today&cc=yes&format=json&q="

    fun getCities(cityName: String, completionHandler: (response: MutableList<City>) -> Unit) {
        val request = JsonObjectRequest(
            Request.Method.GET, cityUrl + cityName, null,
            Response.Listener<JSONObject> { response ->
                completionHandler(computeCityResponse(response))
            },
            Response.ErrorListener {
                //Empty list in case no answer or error.
                completionHandler(mutableListOf())
            })

        VolleyService.getInstance(context).addToRequestQueue(request)
    }

    fun getCityWeather(city: City, completionHandler: (response: City?, error: String?) -> Unit) {
        val request = JsonObjectRequest(
            Request.Method.GET, weatherUrl + city.latitude + "," + city.longitude, null,
            Response.Listener<JSONObject> { response ->
                val cityResponse = computeWeatherResponse(city, response)
                if (cityResponse.weather != null) {
                    // Display Weather information
                    completionHandler(cityResponse, null)
                } else {
                    Log.d("WEATHER", "WARNING: Something it's wrong with the parsing")
                    completionHandler(null, "Cannot parse the city weather")
                }
            },
            Response.ErrorListener {
                Log.e("WEATHER", "Volley Error: ", it)
                //Empty list in case no answer or error.
                completionHandler(
                    null,
                    "Cannot find the weather"
                )
            })

        VolleyService.getInstance(context).addToRequestQueue(request)
    }

    /**
     * Return same City with additional weather information
     */
    private fun computeWeatherResponse(
        city: City,
        response: JSONObject
    ): @ParameterName(name = "response") City {
        try {
            val weatherJsonObj = response
                .getJSONObject("data")
                .getJSONArray("current_condition")

            if (weatherJsonObj.length() > 0) {
                val currentWeather = weatherJsonObj.getJSONObject(0)

                val cityWeather = CityWeather(
                    currentWeather.getInt("temp_C"),
                    currentWeather.getJSONArray("weatherIconUrl").getJSONObject(0)
                        .getString("value"),
                    currentWeather.getJSONArray("weatherDesc").getJSONObject(0)
                        .getString("value"),
                    currentWeather.getDouble("humidity")
                )

                //Add Weather to current city
                city.weather = cityWeather

            } else {
                Log.d("WEATHER", "The request doesn't have any answer")
            }
        } catch (ex: JSONException) {
            Log.d("WEATHER", "Error parsing the answer")
        } finally {
            //Callback. Empty list in case no answer or error.
            return city
        }
    }

    private fun computeCityResponse(response: JSONObject): MutableList<City> {
        val cityList = mutableListOf<City>()
        try {
            val cities = response
                .getJSONObject("search_api")
                .getJSONArray("result")

            if (cities.length() > 0) {
                var index = 0
                while (index < cities.length()) {
                    val cityJsonObj = cities.getJSONObject(index)
                    cityList.add(
                        City(
                            buildCityName(cityJsonObj),
                            cityJsonObj.getDouble("latitude"),
                            cityJsonObj.getDouble("longitude")
                        )
                    )
                    index++
                }
                Log.d("WEATHER", "CityList: ${cityList}")
            } else {
                Log.d("WEATHER", "The request doesn't have any answer")
            }
        } catch (ex: JSONException) {
            Log.d("WEATHER", "Error parsing the answer")
        } finally {
            //Callback. Empty list in case no answer or error.
            return cityList
        }
    }

    /**
     * Create the city name by joining city + region + country.
     * If no region then displayed name is = "City, Country"
     */
    private fun buildCityName(cityJsonObj: JSONObject): String {
        val region = cityJsonObj.getJSONArray("region").getJSONObject(0)
            .getString("value")
        val regionStr = if (region.isEmpty()) {
            ""
        } else {
            ", $region"
        }
        return "${cityJsonObj.getJSONArray("areaName").getJSONObject(0)
            .getString("value")}$regionStr, ${cityJsonObj.getJSONArray("country").getJSONObject(0)
            .getString("value")}"
    }
}