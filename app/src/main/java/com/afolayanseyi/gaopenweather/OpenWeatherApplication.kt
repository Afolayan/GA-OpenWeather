package com.afolayanseyi.gaopenweather

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.afolayanseyi.gaopenweather.di.AppComponent
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent

class OpenWeatherApplication : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        var instance: OpenWeatherApplication? = null

        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as OpenWeatherApplication).appComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent.inject(this)
    }
}