package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.EXCLUDE_LIST
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.METRIC
import com.afolayanseyi.gaopenweather.util.API_KEY
import javax.inject.Inject

open class FetchWeatherForecastUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    open fun fetchWeatherForecast(
        latitude: Double? = 0.0,
        longitude: Double? = 0.0,
        exclude: String? = EXCLUDE_LIST,
        apiKey: String? = API_KEY,
        unit: String? = METRIC
    ) =
        repository.fetchWeatherForecastByCoordinates(latitude, longitude, exclude, apiKey, unit)
}