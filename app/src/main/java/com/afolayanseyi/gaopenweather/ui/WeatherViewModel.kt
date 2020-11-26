package com.afolayanseyi.gaopenweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.data.Resource
import com.afolayanseyi.gaopenweather.domain.FetchWeatherForecastUseCase
import com.afolayanseyi.gaopenweather.extensions.setError
import com.afolayanseyi.gaopenweather.extensions.setLoading
import com.afolayanseyi.gaopenweather.extensions.setSuccess
import com.afolayanseyi.gaopenweather.model.Coordinates
import com.afolayanseyi.gaopenweather.model.CurrentWeatherUI
import com.afolayanseyi.gaopenweather.model.FullWeeklyData
import com.afolayanseyi.gaopenweather.util.SchedulerInterface
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val fetchWeatherForecastUseCase: FetchWeatherForecastUseCase,
    private val appScheduler: SchedulerInterface
) : BaseViewModel() {

    private val _currentWeatherLiveData = MutableLiveData<Resource<CurrentWeatherUI>>()
    val currentWeatherLiveData = _currentWeatherLiveData

    private val _weatherForecastLiveData = MutableLiveData<Resource<FullWeeklyData>>()
    val weatherForecastLiveData = _weatherForecastLiveData

    fun fetchWeatherBy(coordinates: Coordinates) {
        addDisposable(
            fetchWeatherForecastUseCase.fetchWeatherForecast(
                coordinates.lat,
                coordinates.lon
            ).doOnSubscribe {
                _currentWeatherLiveData.setLoading()
                _weatherForecastLiveData.setLoading()
            }
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe({
                    it?.let {
                        it.current?.let { current ->
                            _currentWeatherLiveData.setSuccess(current.toCurrentWeatherUI())
                        }
                        _weatherForecastLiveData.setSuccess(it.toFullWeeklyData())
                    }

                }, {
                    _weatherForecastLiveData.setError(Exception(it.message))
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
