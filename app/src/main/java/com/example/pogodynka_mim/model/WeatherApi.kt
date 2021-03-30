package com.example.pogodynka_mim.model

import com.example.pogodynka_mim.model.entities.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getByCityName(@Query("q") city: String, @Query("APPId") key: String ): Call<Data>
}