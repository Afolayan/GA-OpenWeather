package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.model.WeatherData
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.EXCLUDE_LIST
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.METRIC
import com.afolayanseyi.gaopenweather.util.API_KEY
import io.reactivex.Single
import javax.inject.Inject

open class OpenWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService
) {

    fun fetchWeatherForecastByCoordinates(
        latitude: Double? = 0.0,
        longitude: Double? = 0.0,
        exclude: String? = EXCLUDE_LIST,
        apiKey: String? = API_KEY,
        units: String? = METRIC
    ): Single<WeatherData> {
        return openWeatherService.getForecastByCoordinates(latitude, longitude, exclude, apiKey, units)
    }
}