package com.example.pogodynka_mim.model.entities

data class Main(val temp: Double,
                val feels_like: Double,
                val temp_min: Double,
                val temp_max: Double,
                val pressure: Int,
                val humidity: Int)