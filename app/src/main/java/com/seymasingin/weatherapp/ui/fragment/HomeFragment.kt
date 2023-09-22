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
import androidx.lifecycle.Observer
import com.seymasingin.weatherapp.databinding.FragmentHomeBinding
import com.seymasingin.weatherapp.ui.viewmodel.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeFragment : Fragment() {

private lateinit var binding :FragmentHomeBinding
private val viewModel by lazy { HomeViewModel()}

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
            it?.let{
                binding.textCity.text = it.location.name
                binding.textWeather.text = it.current.temp_c.toString()
                val tempC = it.current.temp_c
                val backgroundPic = viewModel.getWeatherConditionBackground(tempC)
                binding.homeBackground.setBackgroundResource(backgroundPic)

                binding.dayOneMax.text = it.forecast.forecastday[0].day.maxtemp_c.toInt().toString()
                binding.dayOneMin.text = it.forecast.forecastday[0].day.mintemp_c.toInt().toString()
                binding.dayTwoMax.text = it.forecast.forecastday[1].day.maxtemp_c.toInt().toString()
                binding.dayTwoMin.text = it.forecast.forecastday[1].day.mintemp_c.toInt().toString()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

                for (i in 0 until 2) {
                    val dateString = it.forecast.forecastday[i].date
                    val date = LocalDate.parse(dateString, formatter)
                    val dayOfWeek = date.dayOfWeek.toString()

                    when (i) {
                        0 -> binding.textDayOne.text = dayOfWeek
                        1 -> binding.textDayTwo.text = dayOfWeek
                    }
                }
            }
        })
    }
}