package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.model.CurrentWeather
import com.afolayanseyi.gaopenweather.model.WeeklyWeatherData
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import io.reactivex.Single
import javax.inject.Inject

open class OpenWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService
) {
    fun fetchCurrentWeatherByCityName(cityName: String?, apiKey: String?): Single<CurrentWeather> {
        return openWeatherService.getCurrentWeatherByCityName(cityName, apiKey)
    }

    fun fetchCurrentWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        apiKey: String?
    ): Single<CurrentWeather> {
        return openWeatherService.getCurrentWeatherByCoordinates(latitude, longitude, apiKey)
    }

    fun fetchWeatherForecastByCoordinates(
        latitude: Double,
        longitude: Double,
        exclude: String?,
        apiKey: String?
    ): Single<WeeklyWeatherData> {
        return openWeatherService.getForecastByCoordinates(latitude, longitude, exclude, apiKey)
    }
}