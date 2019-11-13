package com.viseo.poc.myweatherapplication.data

import android.content.Context
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
        "https://api.worldweatheronline.com/premium/v1/search.ashx?key=73d5fed1b60c4cf6925100740190711&format=json&query=bogota"

    fun getCities(cityName: String, completionHandler: (response: MutableList<City>) -> Unit) {
        val request = JsonObjectRequest(
            Request.Method.GET, cityUrl + cityName, null,
            Response.Listener<JSONObject> { response ->
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
                                    index.toString(),
                                    cityJsonObj.getJSONArray("areaName").getJSONObject(0)
                                        .getString("value"),
                                    cityJsonObj.getDouble("latitude"),
                                    cityJsonObj.getDouble("longitude")
                                )
                            )
                            index++
                        }
                        println(cityList)
                    } else {
                        println("The request doesn't have any answer")
                    }
                } catch (ex: JSONException) {
                    println("The request doesn't have any answer")
                } finally {
                    //Callback. Empty list in case no answer or error.
                    completionHandler(cityList)
                }
            },
            Response.ErrorListener {
                println("Ay Dios mio!")
                //Empty list in case no answer or error.
                completionHandler(mutableListOf())
            })

        VolleyService.requestQueue.add(request)
        VolleyService.requestQueue.start()
    }
}