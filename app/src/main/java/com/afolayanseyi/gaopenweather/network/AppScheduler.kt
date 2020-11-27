package com.afolayanseyi.gaopenweather.network

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppScheduler : SchedulerInterface {
    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun io() = Schedulers.io()
}
