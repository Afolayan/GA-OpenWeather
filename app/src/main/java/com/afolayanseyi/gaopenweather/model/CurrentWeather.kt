package com.afolayanseyi.gaopenweather.model

class Coord (
    var lon: Double = 0.0,
    var lat: Double = 0.0
)

class Weather {
    var id = 0
    var main: String? = null
    var description: String? = null
    var icon: String? = null
}

class Main {
    var temp = 0.0
    var feels_like = 0.0
    var temp_min = 0.0
    var temp_max = 0.0
    var pressure = 0
    var humidity = 0
}

class Wind {
    var speed = 0.0
    var deg = 0
}

class Clouds {
    var all = 0
}

class Sys {
    var type = 0
    var id = 0
    var message = 0.0
    var country: String? = null
    var sunrise = 0
    var sunset = 0
}

data class CurrentWeather(
    var coord: Coord? = null,
    var weather: List<Weather>? = null,
    var base: String? = null,
    var main: Main? = null,
    var visibility: Int = 0,
    var wind: Wind? = null,
    var clouds: Clouds? = null,
    var dt: Long = 0,
    var sys: Sys? = null,
    var timezone: Int = 0,
    var id: Long = 0,
    var name: String? = null,
    var cod: Int = 0
)
