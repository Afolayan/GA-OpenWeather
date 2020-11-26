package com.afolayanseyi.gaopenweather.util

import io.reactivex.Scheduler

interface SchedulerInterface {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}
