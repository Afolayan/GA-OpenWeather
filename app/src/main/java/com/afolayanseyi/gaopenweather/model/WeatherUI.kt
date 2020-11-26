package com.afolayanseyi.gaopenweather.model

data class CurrentWeatherUI(
    var name: String? = "",
    var temperature: Double? = 0.0,
    var feelsLike: Double? = 0.0,
    var wind: Double? = 0.0,
    var uvIndex: Double? = 0.0,
    var humidity: Int? = 0
)

data class WeeklyData(
    var date: Long,
    var minTemp: Double?,
    var maxTemp: Double?,
    var description: String? = ""
)

data class FullWeeklyData(
    var weeklyData: List<WeeklyData>?,
    var currentWeatherUI: CurrentWeatherUI?
)