package com.example.agrovisionpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.agrovisionpro.DetectFragment
import com.example.agrovisionpro.ui.fragment.ProfileFragment
import com.example.agrovisionpro.R
import com.example.agrovisionpro.databinding.ActivityMainBinding
import com.example.agrovisionpro.ui.fragment.WeatherFragment
import com.example.agrovisionpro.ui.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var currentFragment: String = "home"
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        loadFragment(HomeFragment())
        currentFragment = "home"

        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {
                    if (currentFragment != "home") {
                        loadFragment(HomeFragment())
                        currentFragment = "home"
                    }
                }

                R.id.nav_detect -> {
                    if (currentFragment != "detect") {
                        loadFragment(DetectFragment())
                        currentFragment = "detect"
                    }
                }

                R.id.nav_weather -> {
                    if (currentFragment != "weather") {
                        loadFragment(WeatherFragment())
                        currentFragment = "weather"
                    }
                }

                R.id.nav_profile -> {
                    if (currentFragment != "profile") {
                        loadFragment(ProfileFragment())
                        currentFragment = "profile"
                    }
                }
            }

            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}