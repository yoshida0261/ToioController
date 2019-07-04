package com.stah.toio.lib

import com.stah.toio.lib.session.BleSessionManager
import io.reactivex.Observable

class MotionSensor(sessionManager: BleSessionManager) {
    private val sessionManager = sessionManager

    fun collision() : Observable<ByteArray> =
            sessionManager.connection.flatMapSingle{
                it.readCharacteristic(ToioCube.MOTION_UUID)
            }.doOnNext{
                println(it)
            }



}