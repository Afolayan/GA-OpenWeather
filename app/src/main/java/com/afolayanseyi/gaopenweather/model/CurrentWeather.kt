package com.afolayanseyi.gaopenweather.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

open class CurrentWeather(
    var name: String = "",
    @SerializedName("feels_like")
    var feelsLike: Double = 0.0,
    @SerializedName("dt")
    var date: Long = 0L,
    var sunrise: Long = 0L,
    var sunset: Long = 0L,
    @SerializedName("temp")
    var temperature: Double = 0.0,
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
    var weather: List<Weather>? = null
) {
    override fun toString(): String {
        return Gson().toJson(this).toString()
    }

    fun toCurrentWeatherUI() = CurrentWeatherUI(
        date = date,
        name = name,
        temperature = temperature,
        feelsLike = feelsLike,
        wind = wind_speed,
        uvIndex = uvi,
        humidity = humidity,
        icon = weather?.get(0)?.icon,
        description = weather?.get(0)?.description
    )
}