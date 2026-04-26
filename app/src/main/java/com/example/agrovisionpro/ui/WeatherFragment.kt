package com.example.agrovisionpro.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.agrovisionpro.R
import com.example.agrovisionpro.api.RetrofitClient
import com.example.agrovisionpro.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val cityInput = view.findViewById<EditText>(R.id.etCity)
        val btn = view.findViewById<Button>(R.id.btnGetWeather)
        val result = view.findViewById<TextView>(R.id.txtWeather)

        btn.setOnClickListener {

            val city = cityInput.text.toString()

            RetrofitClient.instance.getWeather(
                city,
                "55ca6b4bc309316ab5eff1efd90ba331"
            ).enqueue(object : Callback<WeatherResponse> {

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    val data = response.body()

                    result.text = """
                        Temp: ${data?.main?.temp}°C
                        Humidity: ${data?.main?.humidity}%
                        Condition: ${data?.weather?.get(0)?.main}
                    """.trimIndent()
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    result.text = "Failed to load weather"
                }
            })
        }
    }
}