package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.util.API_KEY
import javax.inject.Inject

class FetchCurrentWeatherByCoordinatesUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    fun fetchCurrentWeatherByCoordinates(latitude: Double, longitude: Double) =
        repository.fetchCurrentWeatherByCoordinates(latitude, longitude, API_KEY)
}
