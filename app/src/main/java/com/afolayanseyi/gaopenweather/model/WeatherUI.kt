package com.afolayanseyi.gaopenweather.model

data class CurrentWeatherUI(
    var icon: String? = "",
    var description: String? = "",
    var temperature: Double? = 0.0,
    var feelsLike: Double? = 0.0,
    var wind: Double? = 0.0,
    var uvIndex: Double? = 0.0,
    var date: Long,
    var humidity: Int? = 0
)

data class WeekDay(
    var date: Long,
    var minTemp: Double?,
    var maxTemp: Double?,
    var temperature: Double? = 0.0,
    var description: String? = "",
    var icon: String? = ""
)

data class WeatherUIData(
    var weeklyData: List<WeekDay>?,
    var currentWeatherUI: CurrentWeatherUI?
)