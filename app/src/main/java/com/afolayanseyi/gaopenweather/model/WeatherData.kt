package com.afolayanseyi.gaopenweather.model

import com.google.gson.Gson

data class Temp(
    var day: Double = 0.0,
    var min: Double = 0.0,
    var max: Double = 0.0,
    var night: Double = 0.0,
    var eve: Double = 0.0,
    var morn: Double = 0.0
)

data class Weather(
    var id: Long = 0,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)

data class Coordinates(
    var lat: Double = 0.0,
    var lon: Double = 0.0,
)

data class WeatherData(
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var timezone: String? = null,
    var timezone_offset: Int = 0,
    var current: CurrentWeather? = null,
    var daily: List<DailyData>? = null
) {
    override fun toString(): String {
        return Gson().toJson(this).toString()
    }

    fun toFullWeeklyData(): FullWeeklyData {
        val weeklyData: MutableList<WeekDay> = mutableListOf()
        daily?.forEach {
            weeklyData.add(it.toWeeklyData())
        }
        val currentWeatherUI = current?.toCurrentWeatherUI()
        return FullWeeklyData(weeklyData, currentWeatherUI)
    }
}