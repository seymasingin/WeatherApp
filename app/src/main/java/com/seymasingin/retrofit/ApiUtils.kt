package com.seymasingin.retrofit

import com.seymasingin.data.model.WeatherData
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    val BASE_URL = "http://api.weatherapi.com/v1/"
    val apiKey = "5f84a809fae644b996791850230705"
    private val api = Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .build()
                            .create(WeatherDao::class.java)

    fun getData(location: String, days: Int, aqi: String, alerts: String): Single<WeatherData> {
        return api.getWeather(apiKey, location, days, aqi, alerts)
    }
}