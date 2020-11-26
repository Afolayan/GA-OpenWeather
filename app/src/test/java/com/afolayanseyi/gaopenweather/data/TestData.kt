package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.model.Coord
import com.afolayanseyi.gaopenweather.model.CurrentWeather
import com.afolayanseyi.gaopenweather.model.WeeklyWeatherData

object TestData {
    fun generateRandomCurrentWeather(cityName: String): CurrentWeather {
        return CurrentWeather(
            coord = Coord(-122.08, 37.39),
            name = cityName,
        )
    }
    fun generateRandomCurrentWeatherByCoordinates(latitude: Double, longitude: Double): CurrentWeather {
        return CurrentWeather(
            coord = Coord(longitude, latitude),
            name = "Mountain View",
            timezone = -25200,
            dt = 1560350645L
        )
    }
    fun generateRandomWeatherForecastByCoordinates(latitude: Double, longitude: Double): WeeklyWeatherData {
        return WeeklyWeatherData(
            lat = latitude,
            lon = longitude,
            timezone_offset = 1
        )
    }
}