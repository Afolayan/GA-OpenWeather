package com.afolayanseyi.gaopenweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afolayanseyi.gaopenweather.domain.FetchWeatherForecastUseCase
import com.afolayanseyi.gaopenweather.network.SchedulerInterface
import javax.inject.Inject

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