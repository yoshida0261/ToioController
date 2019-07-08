package com.stah.toio.lib

import com.stah.toio.lib.session.BleSessionManager

/**

# 点灯・消灯

0	UInt8	制御の種類	0x03（点灯・消灯）
1	UInt8	ランプを制御する時間	0x10（160 ミリ秒）
2	UInt8	制御するランプの数	0x01
3	UInt8	制御するランプの ID	0x01
4	UInt8	ランプの Red の値	0xFF
5	UInt8	ランプの Green の値	0x00
6	UInt8	ランプの Blue の値	0x00

# 連続的な点灯・消灯

0	UInt8	制御の種類	0x04（連続的な点灯・消灯）
1	UInt8	繰り返し回数	0x00（無限）
2	UInt8	Operation の数	0x02（Operation 2 つ）
3	UInt8	ランプを制御する時間	0x1E（300 ミリ秒）
4	UInt8	制御するランプの数	0x01
5	UInt8	制御するランプの ID	0x01
6	UInt8	ランプの Red の値	0x00
7	UInt8	ランプの Green の値	0xFF
8	UInt8	ランプの Blue の値	0x00
9	UInt8	ランプを制御する時間	0x1E（300 ミリ秒）
10	UInt8	制御するランプの数	0x01
11	UInt8	制御するランプの ID	0x01
12	UInt8	ランプの Red の値	0x00
13	UInt8	ランプの Green の値	0x00
14	UInt8	ランプの Blue の値	0xFF
 */
class Led(sessionManager: BleSessionManager) {

    val sessionManager = sessionManager

    companion object {
        const val ONCE: Byte = 0x03
        const val LOOP: Byte = 0x04


    }


    /*
        fun getBattery(): Observable<ByteArray> =
        sessionManager.connection.flatMapSingle {
            it.readCharacteristic(ToioCube.BATTERY_UUID)
        }.doOnNext {
            println("toio cube $it")
        }

     */
}