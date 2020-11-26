package com.afolayanseyi.gaopenweather.network

import com.afolayanseyi.gaopenweather.model.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    companion object {
        const val QUERY_KEY = "q"
        const val APPLICATION_ID = "appid"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
        const val EXCLUDE = "exclude"
        const val WEATHER = "weather"
        const val ONECALL = "onecall"
        const val HOURLY = "hourly"
    }

    @GET(ONECALL)
    fun getForecastByCoordinates(
        @Query(LATITUDE) latitude: Double?,
        @Query(LONGITUDE) longitude: Double?,
        @Query(EXCLUDE) exclude: String?,
        @Query(APPLICATION_ID) apiKey: String?
    ): Single<WeatherData>
}