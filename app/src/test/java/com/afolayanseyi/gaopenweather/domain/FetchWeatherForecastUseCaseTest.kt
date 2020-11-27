package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.data.TestData
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
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
    private val mockRepository: OpenWeatherRepository = mock()
    private val mockWeatherService: OpenWeatherService = mock()

    private val weatherForecast =
        TestData.generateRandomWeatherForecastByCoordinates(12.0, 13.0)

    @Before
    fun setUp() {
        weatherForecastUseCase = FetchWeatherForecastUseCase(
            mockRepository
        )
    }

    @Test
    fun fetchWeatherForecastSingle_withCoordinates_returnWeatherData() {
        whenever(mockRepository.fetchWeatherForecastByCoordinates(any(), any(), any(), any(), any()))
            .thenReturn(Single.just(weatherForecast))
        val test = weatherForecastUseCase.fetchWeatherForecast(12.0, 13.0).test()

        verify(mockRepository).fetchWeatherForecastByCoordinates(12.0, 13.0)

        test.run {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            val currentWeather = test.values()[0]
            Assert.assertNotNull(currentWeather)
            Assert.assertEquals(12.0, currentWeather.lat)
        }
    }
}