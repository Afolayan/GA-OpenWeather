package com.afolayanseyi.gaopenweather

import android.app.Activity
import android.app.Application
import android.content.Context
import com.afolayanseyi.gaopenweather.di.AppComponent
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent
import com.afolayanseyi.gaopenweather.di.module.CoreModule

class OpenWeatherApplication: Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().coreModule(CoreModule).build()
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as OpenWeatherApplication).appComponent
    }
}

fun Activity.appComponent() = OpenWeatherApplication.appComponent(this)