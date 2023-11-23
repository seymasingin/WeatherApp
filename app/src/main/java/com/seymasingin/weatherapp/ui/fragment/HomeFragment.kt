package com.seymasingin.weatherapp.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.seymasingin.weatherapp.R
import com.seymasingin.weatherapp.databinding.FragmentHomeBinding
import com.seymasingin.weatherapp.ui.adapter.HourAdapter
import com.seymasingin.weatherapp.ui.viewmodel.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding

    private val viewModel by lazy { HomeViewModel()}

    private val hourAdapter = HourAdapter()

    private var isNightMode = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDefaultWeather()

        binding.textInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val userInputLocation = editable.toString()
                viewModel.getWeatherViewModel(userInputLocation)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            it?.let {
                with(binding) {
                    textCity.text = it.location.name
                    textCountry.text = it.location.country
                    textCondition.text = it.current.condition.text
                    textWeather.text = it.current.temp_c.toInt().toString()
                    Glide.with(iconCurrent).load("https:" + it.current.condition.icon).into(iconCurrent)

                    iconTheme.setOnClickListener {
                        isNightMode = !isNightMode
                        applyTheme(isNightMode)
                    }

                    rvHour.adapter = hourAdapter
                    hourAdapter.updateList(it.forecast.forecastday[0].hour)

                    dayOneMax.text = it.forecast.forecastday[0].day.maxtemp_c.toInt().toString()
                    dayOneMin.text = it.forecast.forecastday[0].day.mintemp_c.toInt().toString()
                    dayTwoMax.text = it.forecast.forecastday[1].day.maxtemp_c.toInt().toString()
                    dayTwoMin.text = it.forecast.forecastday[1].day.mintemp_c.toInt().toString()

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

                    for (i in 0 until 2) {
                        val dateString = it.forecast.forecastday[i].date
                        val date = LocalDate.parse(dateString, formatter)
                        val dayOfWeek = date.dayOfWeek.toString()

                        when (i) {
                            0 -> textDayOne.text = dayOfWeek
                            1 -> textDayTwo.text = dayOfWeek
                        }
                    }
                }
            }
        })
    }

    private fun applyTheme(isNightMode: Boolean) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}