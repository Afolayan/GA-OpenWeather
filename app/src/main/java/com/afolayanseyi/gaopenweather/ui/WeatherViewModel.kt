package com.afolayanseyi.gaopenweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.data.Resource
import com.afolayanseyi.gaopenweather.domain.FetchWeatherForecastUseCase
import com.afolayanseyi.gaopenweather.extensions.setError
import com.afolayanseyi.gaopenweather.extensions.setLoading
import com.afolayanseyi.gaopenweather.extensions.setSuccess
import com.afolayanseyi.gaopenweather.model.Coordinates
import com.afolayanseyi.gaopenweather.model.CurrentWeatherUI
import com.afolayanseyi.gaopenweather.model.WeatherUIData
import com.afolayanseyi.gaopenweather.network.SchedulerInterface
import com.afolayanseyi.gaopenweather.util.LocationUtil
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val fetchWeatherForecastUseCase: FetchWeatherForecastUseCase,
    private val appScheduler: SchedulerInterface
) : BaseViewModel() {

    private val _currentWeatherLiveData = MutableLiveData<Resource<CurrentWeatherUI>>()
    val currentWeatherLiveData = _currentWeatherLiveData

    private val _weatherForecastLiveData = MutableLiveData<Resource<WeatherUIData>>()
    val weatherForecastLiveData = _weatherForecastLiveData
    val addressLiveData = MutableLiveData<String>()

    var coordinateAddress: String? = ""

    fun fetchWeatherBy(coordinates: Coordinates) {
        fetchWeatherForecastUseCase.fetchWeatherForecast(
            coordinates.lat,
            coordinates.lon
        )?.doOnSubscribe {
            _currentWeatherLiveData.setLoading()
            _weatherForecastLiveData.setLoading()
        }?.subscribeOn(appScheduler.io())
            ?.observeOn(appScheduler.mainThread())?.let {
                addDisposable(
                    it.subscribe({
                        it?.let {
                            it.current?.let { current ->
                                _currentWeatherLiveData.setSuccess(current.toCurrentWeatherUI())
                            }
                            _weatherForecastLiveData.setSuccess(it.toWeatherUIData())
                        }

                    }, {
                        _weatherForecastLiveData.setError(Exception(it.message))
                    })
                )
            }
    }

    fun fetchLocationAddress(latitude: Double, longitude: Double) {
        addDisposable(
            LocationUtil.getAddressFromLocation(
                OpenWeatherApplication.instance!!,
                latitude, longitude
            )
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe({
                    coordinateAddress = it
                    addressLiveData.postValue(it)
                }, {

                })
        )
    }
}

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory @Inject constructor(
    private val fetchWeatherForecastUseCase: FetchWeatherForecastUseCase,
    private val appScheduler: SchedulerInterface
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            fetchWeatherForecastUseCase,
            appScheduler
        ) as T
    }
}
