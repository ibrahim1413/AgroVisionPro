package com.example.agrovisionpro.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrovisionpro.model.source.Resource
import com.example.agrovisionpro.model.weather.WeatherResponse
import com.example.agrovisionpro.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?>
        get() = _errorMsg

    // Weather Api Response==================================================================
    private val _currentWeatherResp = MutableStateFlow<WeatherResponse?>(null)
    val currentWeatherResp: StateFlow<WeatherResponse?> get() = _currentWeatherResp

    // Api Call==================================================================
    fun getCurrentWeather(city: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            when (val resource = repository.getWeatherData(city = city, null, null)) {

                is Resource.Error -> {
                    _loading.value = false
                    _errorMsg.value = resource.message
                }

                is Resource.Loading -> {
                    _loading.value = true
                }

                is Resource.Success -> {
                    _loading.value = false
                    _currentWeatherResp.value = resource.data
                }

                else -> {}
            }
        } catch (e: Exception) {
            Log.e("API_TAG", "getCurrentWeatherResp: api call error: $e")
        }
    }
}