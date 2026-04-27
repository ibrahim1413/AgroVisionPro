package com.example.agrovisionpro.repository

import android.util.Log
import com.example.agrovisionpro.api.ApiService
import com.example.agrovisionpro.utils.removeMapNullValues
import com.example.agrovisionpro.BuildConfig
import com.example.agrovisionpro.model.source.Resource
import com.example.agrovisionpro.model.weather.WeatherResponse
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getWeatherData(
        city: String?,
        lat: String?,
        lon: String?
    ): Resource<WeatherResponse> {
        return try {
            val response = api.getWeatherData(
                removeMapNullValues(
                    mapOf(
                        "appid" to BuildConfig.API_KEY,
                        "lat" to lat,
                        "lon" to lon,
                        "q" to city
                    )
                )
            )
            Resource.Success(response)

        } catch (e: Exception) {
            Log.e("api_tag", "getCurrentWeatherResp: Error: ${e.message}")

            Resource.Error(
                message = e.message ?: "An unknown error occurred"
            )
        }
    }

}