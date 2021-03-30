package com.example.pogodynka_mim.model.entities

data class Data(val coord: Coord,
                val weather: List<Weather>,
                val main: Main,
                val visibility: Int,
                val wind: Wind,
                val sys: Sys,
                val name: String)