package com.stah.toiocontroller

import android.app.Application
import com.stah.toiocontroller.usecase.cube.MoveBackUseCase
import com.stah.toiocontroller.usecase.impl.cube.MoveBackUseCaseImpl
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
        factory { MoveBackUseCaseImpl() as MoveBackUseCase }
    }
}