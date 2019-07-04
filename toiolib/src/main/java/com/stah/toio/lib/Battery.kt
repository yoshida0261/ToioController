package com.stah.toio.lib

import com.stah.toio.lib.session.BleSessionManager
import io.reactivex.Observable

/**
 *
 * バッテリー残量
 * バッテリー残量は 0 から 100 までの範囲を 10 刻みで取得可能です。単位はパーセントです。
 *
 * 0	UInt8	バッテリー残量	0x50（80 パーセント）
 */
class Battery(sessionManager: BleSessionManager) {

    private val sessionManager = sessionManager

    fun getBattery(): Observable<ByteArray> =
        sessionManager.connection.flatMapSingle {
            it.readCharacteristic(ToioCube.BATTERY_UUID)
        }.doOnNext {
            println("toio cube $it")
        }


}