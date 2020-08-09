package com.stah.toiocontroller.activity.scan

import android.content.Context
import androidx.core.content.contentValuesOf
import com.polidea.rxandroidble2.RxBleClient
import com.stah.toiocontroller.cube.DefaultToioCubeScan
import com.stah.toiocontroller.cube.ToioCubeScan
import com.stah.toiocontroller.infra.repository.ToioRepositoryImpl
import com.stah.toiocontroller.usecase.impl.cube.MoveUseCaseImpl

class ScanViewModel (val toioCubeScan : ToioCubeScan){




    fun scanToioCube(){
        toioCubeScan.start()
    }

    fun ledStart(){

    }

    fun scanTerminate(){
        toioCubeScan.stop()
    }



}