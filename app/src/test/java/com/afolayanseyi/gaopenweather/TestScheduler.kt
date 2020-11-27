package com.afolayanseyi.gaopenweather

import com.afolayanseyi.gaopenweather.network.SchedulerInterface
import io.reactivex.schedulers.Schedulers

class TestScheduler : SchedulerInterface {

    override fun mainThread(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }
}
