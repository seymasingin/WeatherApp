package com.seymasingin.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seymasingin.data.model.Location
import com.seymasingin.data.model.WeatherData
import com.seymasingin.retrofit.ApiUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel: ViewModel() {
        val weatherData = MutableLiveData<WeatherData>()
        private val apiUtils = ApiUtils()
        private val disposable = CompositeDisposable()


fun getWeatherViewModel(location: String){
        disposable.add(
            apiUtils.getData(location, 3 , "no", "no")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherData>(){
                    override fun onSuccess(t: WeatherData) {
                        weatherData.value= t
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}