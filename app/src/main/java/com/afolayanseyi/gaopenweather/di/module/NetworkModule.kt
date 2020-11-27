package com.afolayanseyi.gaopenweather.di.module

import com.afolayanseyi.gaopenweather.data.OpenWeatherRepository
import com.afolayanseyi.gaopenweather.network.OpenWeatherService
import com.afolayanseyi.gaopenweather.imageloader.GlideImageLoader
import com.afolayanseyi.gaopenweather.imageloader.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
object NetworkModule {

    @Provides
    @Reusable
    fun providesImageLoader(): ImageLoader = GlideImageLoader()

    @Provides
    @Reusable
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Reusable
    fun weatherService(retrofit: Retrofit): OpenWeatherService =
        retrofit.create(OpenWeatherService::class.java)

    @Provides
    @Reusable
    fun provideWeatherRepository(openWeatherService: OpenWeatherService): OpenWeatherRepository =
        OpenWeatherRepository(openWeatherService)


}