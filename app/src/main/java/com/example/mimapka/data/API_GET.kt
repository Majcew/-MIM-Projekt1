package com.example.mimapka.data

import com.google.gson.Gson
import java.net.URL

object API_GET {
    var response : String? = null
    fun hitAPI(cityName:String):Response?{
        response = try {
            URL("http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY").readText()
        }catch (e:Exception){
            null
        }
        return Gson().fromJson(response,Response::class.java)
    }
}