package com.stah.toio.lib

import com.stah.toio.lib.session.BleSessionManager

/**
0	UInt8	制御の種類	0x02（時間指定付きモーター制御）
1	UInt8	制御するモーターの ID	0x01（左）
2	UInt8	モーターの回転方向	0x01（前）
3	UInt8	モーターの速度指示値	0x64（100）
4	UInt8	制御するモーターの ID	0x02（右）
5	UInt8	モーターの回転方向	0x02（後）
6	UInt8	モーターの速度指示値	0x14（20）
7	UInt8	モーターの制御時間	0x0A（100 ミリ秒）
 */
class Motor(sessionManager: BleSessionManager) {

    val sessionManager = sessionManager
/*
    fun front() {

        sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })

    }

    override fun stop() {
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x00, 0x02, 0x01, 0x00))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
    }

    override fun back() {
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x02, 0x64, 0x02, 0x02, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
    }

    override fun right() {
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x02, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
    }

    override fun left() {
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x02, 0x64, 0x02, 0x01, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
    }
*/
}