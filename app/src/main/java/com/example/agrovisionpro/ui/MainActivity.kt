package com.example.agrovisionpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.agrovisionpro.DetectFragment
import com.example.agrovisionpro.ui.ProfileFragment
import com.example.agrovisionpro.R
import com.example.agrovisionpro.ui.WeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentFragment: String = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())
        currentFragment = "home"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener {

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
}