package com.stah.toiocontroller

import android.app.Application
import android.content.Context
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
        ToioController.rxBleClient(this)

        startKoin { modules(module) }
    }

    object ToioController {
        lateinit var rxBleClient : RxBleClient

        fun rxBleClient(myApp: MyApp) {
            rxBleClient = RxBleClient.create(myApp.applicationContext)
        }
    }


    private val module: Module = module {
        factory { MoveUseCaseImpl(ToioRepositoryImpl(ToioController.rxBleClient)) as MoveUseCase } // castは必要
    }
}