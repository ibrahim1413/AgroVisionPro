package com.example.agrovisionpro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.agrovisionpro.BaseApplication
import com.example.agrovisionpro.R
import com.example.agrovisionpro.databinding.FragmentWeatherBinding
import com.example.agrovisionpro.model.weather.WeatherResponse
import com.example.agrovisionpro.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        val cityInput = view.findViewById<EditText>(R.id.etCity)
//        val btn = view.findViewById<Button>(R.id.btnGetWeather)
//        val result = view.findViewById<TextView>(R.id.txtWeather)

        binding.btnGetWeather.setOnClickListener {

            val city = binding.etCity.text.toString()

            weatherViewModel.getCurrentWeather(city)
            /*
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
            })*/
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect details api call data===================
                launch {
                    weatherViewModel.currentWeatherResp
                        .filterNotNull()
                        .collect { data ->
                            updateUi(data)
                        }
                }
                // Collect error api msg===================
                launch {
                    weatherViewModel.errorMsg
                        .filterNotNull()
                        .collect { data ->
                            binding.txtWeather.text = data ?: "Failed to load weather"
                            //short toast
                            Toast.makeText(BaseApplication.appContext, "Error: $data", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    private fun updateUi(data: WeatherResponse) {
        binding.txtWeather.text = """
                        Temp: ${data?.main?.temp}°C
                        Humidity: ${data?.main?.humidity}%
                        Condition: ${data?.weather?.get(0)?.main}
                    """.trimIndent()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}