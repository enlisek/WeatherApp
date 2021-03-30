package com.example.pogodynka_mim

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import android.provider.Settings.System.getString
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pogodynka_mim.model.WeatherRepository
import com.example.pogodynka_mim.model.entities.Data
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val weatherRepository : WeatherRepository = WeatherRepository()

    var currentCity =  ""


    private var _data:MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data>
        get() = _data

    init {

        currentCity = "Krowiarki"
        updateData()
    }
    fun updateData()
    {
        viewModelScope.launch {
            Log.d("MVM","updateData()")
            _data.value = weatherRepository.getByCityName(currentCity)

        }
    }


}