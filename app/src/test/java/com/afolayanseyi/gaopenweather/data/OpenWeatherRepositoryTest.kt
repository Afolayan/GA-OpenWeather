package com.afolayanseyi.gaopenweather.data

import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.DAILY
import com.afolayanseyi.gaopenweather.util.API_KEY
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations.initMocks
import org.powermock.modules.testng.PowerMockTestCase
import org.testng.annotations.BeforeTest

class OpenWeatherRepositoryTest : PowerMockTestCase() {
    private lateinit var repository: OpenWeatherRepository
    private val openWeatherService: OpenWeatherService = mock()
    private val throwable = Throwable()

    companion object {
        private const val cityName = "Lagos"
        val cityNameWeather = TestData.generateRandomCurrentWeather(cityName)
        val coordinatesWeather = TestData.generateRandomCurrentWeatherByCoordinates(12.0, 13.0)
        val weatherForecast = TestData.generateRandomWeatherForecastByCoordinates(12.0, 13.0)
    }

    @BeforeTest
    fun init() {
        initMocks(this);
    }

    @Before
    fun setup() {
        repository = OpenWeatherRepository(
            openWeatherService
        )
    }

    @Test
    fun fetchCurrentWeather_withCityName_returnSuccess() {
        whenever(repository.fetchCurrentWeatherByCityName(any(), any()))
            .thenReturn(Single.just(cityNameWeather))
        repository.fetchCurrentWeatherByCityName(cityName, API_KEY).test()?.let {
            assert(cityName == cityNameWeather.name)
        }
        verify(openWeatherService).getCurrentWeatherByCityName(cityName, API_KEY)
    }

    @Test
    fun fetchCurrentWeather_withCoordinates_returnSuccess() {
        whenever(repository.fetchCurrentWeatherByCoordinates(any(), any(), any()))
            .thenReturn(Single.just(coordinatesWeather))
        val latitude = 12.0
        val longitude = 13.0
        repository.fetchCurrentWeatherByCoordinates(latitude, longitude, API_KEY).test()?.let {
            assert(latitude == coordinatesWeather.coord?.lat)
        }
        verify(openWeatherService).getCurrentWeatherByCoordinates(latitude, longitude, API_KEY)
    }

    @Test
    fun fetchWeatherForecast_withCoordinates_returnSuccess() {
        whenever(repository.fetchWeatherForecastByCoordinates(any(), any(), any(), any()))
            .thenReturn(Single.just(weatherForecast))
        val latitude = 12.0
        val longitude = 13.0
        repository.fetchWeatherForecastByCoordinates(latitude, longitude, DAILY, API_KEY).test()
            ?.let {
                assert(latitude == coordinatesWeather.coord?.lat)
            }
        verify(openWeatherService).getForecastByCoordinates(latitude, longitude, DAILY, API_KEY)
    }
}