package com.stah.toiocontroller

import android.app.Application
import com.polidea.rxandroidble2.RxBleClient
import com.stah.toiocontroller.infra.repository.ToioRepositoryImpl
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import com.stah.toiocontroller.usecase.impl.cube.MoveUseCaseImpl
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin { modules(module) }

    }

    private val module: Module = module {
        factory { MoveUseCaseImpl(ToioRepositoryImpl(RxBleClient.create(applicationContext))) as MoveUseCase }
    }
}