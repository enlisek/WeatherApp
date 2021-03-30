package com.example.pogodynka_mim.model

import com.example.pogodynka_mim.model.entities.*
import retrofit2.awaitResponse

class WeatherRepository {

    val appid = "56cb12b1fe998887aacc5e4224ec0792"
    suspend fun getByCityName(city: String): Data
    {
        return WeatherService.api.getByCityName(city, "56cb12b1fe998887aacc5e4224ec0792").awaitResponse().body()?:Data(Coord(0.0,0.0), listOf(),Main(0.0,0.0,0.0,0.0,0,0),0,Wind(0.0,0), Sys("",0,0),"ERROR")
    }
}