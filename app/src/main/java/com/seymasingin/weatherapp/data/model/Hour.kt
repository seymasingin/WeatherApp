package com.seymasingin.weatherapp.data.model

data class Hour(
    val condition: Condition,
    val time: String,
    val temp_c : Double
)
