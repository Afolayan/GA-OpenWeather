package com.afolayanseyi.gaopenweather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afolayanseyi.gaopenweather.data.Resource
import com.afolayanseyi.gaopenweather.data.ResourceState
import com.afolayanseyi.gaopenweather.data.TestData
import com.afolayanseyi.gaopenweather.domain.FetchWeatherForecastUseCase
import com.afolayanseyi.gaopenweather.model.Coordinates
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel
    private val mockFetchWeatherForecastUseCase: FetchWeatherForecastUseCase = mock()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        viewModel =
            WeatherViewModel(
                mockFetchWeatherForecastUseCase,
                com.afolayanseyi.gaopenweather.TestScheduler()
            )
        RxJavaPlugins.setComputationSchedulerHandler {
            testScheduler
        }
    }

    @Test
    fun fetchWeatherBy_withCoordinate_returnSuccess() {
        val weatherForecast =
            TestData.generateRandomWeatherForecastByCoordinates(12.0, 13.0)
        val weatherForecastSingle = Single.just(weatherForecast)
        whenever(
            mockFetchWeatherForecastUseCase.fetchWeatherForecast(
                any(), any(), any(), any(), any()
            )
        ).thenReturn(weatherForecastSingle)
        viewModel.fetchWeatherBy(Coordinates(12.0, 13.0))

        Assert.assertEquals(
            Resource(ResourceState.SUCCESS, weatherForecast.toWeatherUIData()),
            viewModel.weatherForecastLiveData.value
        )
    }

    @Test
    fun fetchWeatherBy_withCoordinate_returnError() {
        val exception = Exception()
        whenever(
            mockFetchWeatherForecastUseCase.fetchWeatherForecast(
                any(), any(), any(), any(), any()
            )
        ).thenReturn(Single.error(exception))
        viewModel.fetchWeatherBy(Coordinates(12.0, 13.0))

        verify(mockFetchWeatherForecastUseCase).fetchWeatherForecast(12.0, 13.0)
        Assert.assertEquals(
            exception.message,
            viewModel.weatherForecastLiveData.value?.exception?.message
        )
    }
}