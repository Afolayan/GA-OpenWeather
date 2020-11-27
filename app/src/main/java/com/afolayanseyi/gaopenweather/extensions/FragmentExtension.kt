package com.afolayanseyi.gaopenweather.extensions

import com.afolayanseyi.gaopenweather.OpenWeatherApplication
import com.afolayanseyi.gaopenweather.di.DaggerAppComponent
import com.afolayanseyi.gaopenweather.ui.today.TodayFragment
import com.afolayanseyi.gaopenweather.ui.weekly.WeeklyFragment

fun TodayFragment.inject() {
    DaggerAppComponent.builder()
        .application(OpenWeatherApplication.instance!!)
        .build()
        .inject(this)
}

fun WeeklyFragment.inject() {
    DaggerAppComponent.builder()
        .application(OpenWeatherApplication.instance!!)
        .build()
        .inject(this)
}