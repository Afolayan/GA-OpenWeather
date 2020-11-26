package com.afolayanseyi.gaopenweather.di

import android.app.Application
import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.di.module.AppModule
import com.afolayanseyi.gaopenweather.di.module.NetworkModule
import com.afolayanseyi.gaopenweather.ui.today.TodayFragment
import com.afolayanseyi.gaopenweather.ui.weekly.WeeklyFragment
import com.afolayanseyi.gaopenweather.util.SchedulerInterface
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun scheduler(): SchedulerInterface
    fun provideOkHttpClient(): OkHttpClient
    fun provideGson(): Gson
    fun provideGsonConverterFactory(): GsonConverterFactory

    fun inject(weatherApplication: OpenWeatherApplication)
    fun inject(todayFragment: TodayFragment)
    fun inject(weeklyFragment: WeeklyFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}
