package com.afolayanseyi.gaopenweather.model

import com.google.gson.annotations.SerializedName

data class DailyData(
    @SerializedName("dt")
    var date: Long = 0L,
    var sunrise: Long = 0L,
    var sunset: Long = 0L,
    var pressure: Long = 0L,
    var humidity: Int = 0,
    @SerializedName("dew_point")
    var dew_point: Double = 0.0,
    var uvi: Double = 0.0,
    var clouds: Int = 0,
    var visibility: Long = 0L,
    @SerializedName("wind_speed")
    var wind_speed: Double = 0.0,
    @SerializedName("wind_deg")
    var wind_deg: Double = 0.0,
    var weather: List<Weather>? = null,
    var rain: Double = 0.0,
    var temp: Temp? = null
) {

    fun toWeeklyData(): WeekDay {
        val weeklyData = WeekDay(
            date = date,
            minTemp = temp?.min,
            maxTemp = temp?.max
        )
        var description = ""
        weather?.get(0)?.let {
            weeklyData.icon = it.icon
                description += it.main
            it.description?.let { desc ->
                description += ", "
                description += desc
            }
        }
        weeklyData.description = description
        return weeklyData
    }
}