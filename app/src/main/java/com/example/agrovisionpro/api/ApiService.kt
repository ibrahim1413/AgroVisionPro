package com.example.agrovisionpro.api

import com.example.agrovisionpro.model.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET(APIs.CURRENT_WEATHER)
    suspend fun getWeatherData(
        @QueryMap options: Map<String, String>
    ): WeatherResponse
}