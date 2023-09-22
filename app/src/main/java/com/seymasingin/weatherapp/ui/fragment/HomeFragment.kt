package com.seymasingin.weatherapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.canerture.navigationcomponent.common.viewBinding
import com.seymasingin.weatherapp.R
import com.seymasingin.weatherapp.databinding.FragmentHomeBinding
import com.seymasingin.weatherapp.ui.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

private lateinit var binding :FragmentHomeBinding
private val viewModel by lazy { HomeViewModel()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        })
    }
}