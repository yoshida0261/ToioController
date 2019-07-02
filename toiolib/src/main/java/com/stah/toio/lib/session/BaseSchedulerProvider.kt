package com.stah.toio.lib.session

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}