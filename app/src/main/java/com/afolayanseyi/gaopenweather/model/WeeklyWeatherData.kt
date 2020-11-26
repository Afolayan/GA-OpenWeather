package com.afolayanseyi.gaopenweather.model

class Current {
    var dt = 0
    var sunrise = 0
    var sunset = 0
    var temp = 0.0
    var feels_like = 0.0
    var pressure = 0
    var humidity = 0
    var dew_point = 0.0
    var uvi = 0.0
    var clouds = 0
    var visibility = 0
    var wind_speed = 0.0
    var wind_deg = 0
    var weather: List<Weather>? = null
}

class Temp {
    var day = 0.0
    var min = 0.0
    var max = 0.0
    var night = 0.0
    var eve = 0.0
    var morn = 0.0
}

class FeelsLike {
    var day = 0.0
    var night = 0.0
    var eve = 0.0
    var morn = 0.0
}

class Weather3 {
    var id = 0
    var main: String? = null
    var description: String? = null
    var icon: String? = null
}

class Daily {
    var dt = 0
    var sunrise = 0
    var sunset = 0
    var temp: Temp? = null
    var feels_like: FeelsLike? = null
    var pressure = 0
    var humidity = 0
    var dew_point = 0.0
    var wind_speed = 0.0
    var wind_deg = 0
    var weather: List<Weather3>? = null
    var clouds = 0
    var pop = 0
    var rain = 0.0
    var uvi = 0.0
}

class Alert {
    var sender_name: String? = null
    var event: String? = null
    var start = 0
    var end = 0
    var description: String? = null
}

data class WeeklyWeatherData (
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var timezone: String? = null,
    var timezone_offset: Int = 0,
    var current: Current? = null,
    var daily: List<Daily>? = null,
    var alerts: List<Alert>? = null
)