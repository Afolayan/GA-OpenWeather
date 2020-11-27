package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.model.WeatherData

object TestData {

    fun generateRandomWeatherForecastByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherData {
        return WeatherData(
            lat = latitude,
            lon = longitude,
            timezone_offset = 1,
            timezone = "WAT",

        )
    }
}