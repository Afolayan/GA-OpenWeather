package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.data.TestData
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.EXCLUDE_LIST
import com.afolayanseyi.gaopenweather.network.OpenWeatherService.Companion.UNITS
import com.afolayanseyi.gaopenweather.util.API_KEY
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchWeatherForecastUseCaseTest {

    private lateinit var weatherForecastUseCase: FetchWeatherForecastUseCase
    private lateinit var mockRepository: OpenWeatherRepository
    private val mockWeatherService: OpenWeatherService = mock()

    private val weatherForecast =
        TestData.generateRandomWeatherForecastByCoordinates(12.0, 13.0)

    @Before
    fun setUp() {
        mockRepository = OpenWeatherRepository(mockWeatherService)
        weatherForecastUseCase = FetchWeatherForecastUseCase(
            mockRepository
        )
    }

    @Test
    fun fetchWeatherForecastSingle_withCoordinates_returnWeatherData() {
        whenever(
            mockRepository.fetchWeatherForecastByCoordinates(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        )
            .thenReturn(Single.just(weatherForecast))
        val test = weatherForecastUseCase.fetchWeatherForecast(
            12.0, 13.0,
            EXCLUDE_LIST, API_KEY, UNITS
        )?.test()

        verify(mockWeatherService).getForecastByCoordinates(
            12.0,
            13.0,
            EXCLUDE_LIST,
            API_KEY,
            UNITS
        )

        test?.run {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            val currentWeather = test.values()[0]
            Assert.assertNotNull(currentWeather)
            Assert.assertEquals(12.0, currentWeather.lat)
        }
    }
}