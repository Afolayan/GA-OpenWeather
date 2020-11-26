package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.model.WeatherData

object TestData {
    /*fun generateRandomCurrentWeather(cityName: String): CurrentWeather {
        return CurrentWeather(
            coord = Coordinates(-122.08, 37.39),
            name = cityName,
        )
    }

    fun generateRandomCurrentWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherData {
        return WeatherData (
            latitude,
            longitude,
            timezone = "PDT"
        )
    }*/

    fun generateRandomWeatherForecastByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherData {
        return WeatherData(
            lat = latitude,
            lon = longitude,
            timezone_offset = 1
        )
    }
}