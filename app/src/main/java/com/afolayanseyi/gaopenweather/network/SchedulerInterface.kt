package com.afolayanseyi.gaopenweather.network

import io.reactivex.Scheduler

interface SchedulerInterface {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}
