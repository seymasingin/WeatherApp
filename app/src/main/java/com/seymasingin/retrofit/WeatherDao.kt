package com.seymasingin.retrofit


import com.seymasingin.data.model.WeatherData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherDao {
    @GET("forecast.json")
    fun getWeather(@Query("key") apiKey: String,
                   @Query("q") location: String,
                   @Query("days") days: Int,
                   @Query("aqi") aqi: String,
                   @Query("alerts") alerts: String): Single<WeatherData>
}