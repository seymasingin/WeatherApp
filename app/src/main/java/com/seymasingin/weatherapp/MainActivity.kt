package com.seymasingin.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seymasingin.weatherapp.databinding.ActivityMainBinding
import com.seymasingin.weatherapp.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}