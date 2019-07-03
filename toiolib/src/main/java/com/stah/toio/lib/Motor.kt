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

    fun drive(leftSpeed: Byte, leftDirection: Byte, rightSpeed: Byte, rightDirection: Byte) {
        sessionManager.connection.flatMapSingle {
            it.writeCharacteristic(
                ToioCube.MOTOR_UUID, byteArrayOf(
                    MOTOR_CONTROL,
                    LEFT, leftDirection, leftSpeed,
                    RIGHT, rightDirection, rightSpeed
                )
            )
        }.subscribe({
            println("toio cube $it")
        }, {
            println(it)
        })
    }

    fun front(speed: Byte = 0x64) {
        drive(speed, ADVANCE, speed, ADVANCE)
    }

    fun stop() {
        drive(0x00, ADVANCE, 0x00, ADVANCE)
    }

    fun back(speed: Byte = 0x64) {
        drive(speed, BACK, speed, BACK)
    }

    fun right(speed: Byte = 0x64) {
        drive(speed, ADVANCE, speed, BACK)
    }

    fun left(speed: Byte = 0x64) {
        drive(speed, BACK, speed, ADVANCE)
    }

    companion object {
        const val LEFT: Byte = 0x01
        const val RIGHT: Byte = 0x02
        const val ADVANCE: Byte = 0x01
        const val BACK: Byte = 0x02
        const val MOTOR_TIME_CONTROL: Byte = 0x02
        const val MOTOR_CONTROL: Byte = 0x01

    }


}