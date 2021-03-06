package com.afolayanseyi.gaopenweather.ui

import androidx.lifecycle.MutableLiveData
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
                            fetchLocationAddress(
                                coordinates.lat,
                                coordinates.lon
                            )
                        }

                    }, {
                        _weatherForecastLiveData.setError(Exception(it.message))
                    })
                )
            }
    }

    private fun fetchLocationAddress(latitude: Double, longitude: Double) {
        addDisposable(
            LocationUtil.getAddressFromLocation(
                OpenWeatherApplication.instance!!,
                latitude, longitude
            )
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe { address ->
                    coordinateAddress = address
                    addressLiveData.postValue(address)
                }
        )
    }

    fun getLocationFromAddress(inputAddress: String) {
        addDisposable(
            LocationUtil.getLocationFromAddress(
                inputAddress
            )
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe { latLng ->
                    val lat = latLng.latitude
                    val lon = latLng.longitude
                    if (lat > 0 && lon > 0) {
                        // fetch weather for lat and lng
                        fetchWeatherBy(Coordinates(lat, lon))
                    }
                }
        )
    }
}

