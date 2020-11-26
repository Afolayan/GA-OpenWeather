package com.afolayanseyi.gaopenweather.domain

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.data.TestData
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import com.afolayanseyi.gaopenweather.util.API_KEY
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchCurrentWeatherByCityNameUseCaseTest {

    private lateinit var weatherByCityNameUseCase: FetchCurrentWeatherByCityNameUseCase
    private val mockRepository: OpenWeatherRepository = mock()
    private val mockWeatherService: OpenWeatherService = mock()

    private val cityName = "Lagos"
    private val cityNameWeather = TestData.generateRandomCurrentWeather(cityName)

    @Before
    fun setUp() {
        weatherByCityNameUseCase = FetchCurrentWeatherByCityNameUseCase(
            mockRepository
        )
    }

    @Test
    fun fetchCurrentWeatherSingle_withCityName_returnCurrentWeather() {
        whenever(mockRepository.fetchCurrentWeatherByCityName(any(), any()))
            .thenReturn(Single.just(cityNameWeather))
        val test = weatherByCityNameUseCase.fetchCurrentWeatherByCity(cityName).test()

        verify(mockRepository).fetchCurrentWeatherByCityName(cityName, API_KEY)

        test.run {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            val currentWeather = test.values()[0]
            Assert.assertNotNull(currentWeather)
            Assert.assertEquals(cityName, currentWeather.name)
        }
    }
}