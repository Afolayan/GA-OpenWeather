package com.afolayanseyi.gaopenweather.di

import com.afolayanseyi.gaopenweather.di.module.NetworkModule
import com.afolayanseyi.gaopenweather.di.module.CoreModule
import com.afolayanseyi.gaopenweather.util.SchedulerInterface
import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [CoreModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun scheduler(): SchedulerInterface
    fun provideOkHttpClient(): OkHttpClient
    fun provideGson(): Gson
    fun provideGsonConverterFactory(): GsonConverterFactory
}
