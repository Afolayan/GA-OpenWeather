package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.HOURLY
import com.afolayanseyi.gaopenweather.util.API_KEY
import javax.inject.Inject

class FetchWeatherForecastUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double,
        exclude: String = HOURLY,
        apiKey: String = API_KEY
    ) =
        repository.fetchWeatherForecastByCoordinates(latitude, longitude, exclude, apiKey)
}