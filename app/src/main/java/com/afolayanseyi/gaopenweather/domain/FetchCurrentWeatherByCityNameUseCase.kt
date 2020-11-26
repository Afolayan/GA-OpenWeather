package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.util.API_KEY
import javax.inject.Inject

class FetchCurrentWeatherByCityNameUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    fun fetchCurrentWeatherByCity(city: String) = repository.fetchCurrentWeatherByCityName(city, API_KEY)
}