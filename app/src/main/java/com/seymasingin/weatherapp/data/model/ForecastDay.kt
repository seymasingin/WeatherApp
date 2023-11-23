package com.seymasingin.weatherapp.data.model

data class ForecastDay (
    val day: Day,
    val date : String,
    val hour : List<Hour>
)