package com.stah.toiocontroller.infra.repository

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toio.lib.Motor
import com.stah.toio.lib.ToioCube
import com.stah.toio.lib.session.BleSessionManager
import com.stah.toio.lib.session.SchedulerProvider
import com.stah.toiocontroller.domain.repository.ToioRepository
import io.reactivex.disposables.Disposable
import timber.log.Timber


class ToioRepositoryImpl(val bleClient: RxBleClient) : ToioRepository {

    lateinit var dispose: Disposable
    private val sessionManager = BleSessionManager(bleClient, SchedulerProvider)
    private val motor =  Motor(sessionManager)

    override fun scan() {
        dispose = bleClient.scanBleDevices(
            ScanSettings.Builder().build()
        ).filter {
            println(it.bleDevice.macAddress)
            it.scanRecord.serviceUuids!!.contains(ParcelUuid(ToioCube.SERVICE_UUID))
        }
            .subscribe({
                Timber.d("toio name ${it.bleDevice.name}")
                Timber.d("toio: mac ${it.bleDevice.macAddress}")

                sessionManager.setMacAddress(it.bleDevice.macAddress)
                sessionManager.setupConnection()
                led()

                dispose.dispose()
            }, {
                Timber.e(it.localizedMessage)
            })
    }

    fun led() {
        val dispose = sessionManager.connection.flatMapSingle {

            it.writeCharacteristic(
                ToioCube.LIGHT_UUID, byteArrayOf(
                    0x04, 0x00, 0x01, 0x1E, 0x01, 0x01,
                    0xff.toByte(), 0xff.toByte(), 0xff.toByte()
                )
            )
        }.subscribe({
            println("light $it")
        }, {
            println(it)
        })
    }


    override fun front() {

        /*
        0	UInt8	制御の種類	0x02（時間指定付きモーター制御）
        1	UInt8	制御するモーターの ID	0x01（左）
        2	UInt8	モーターの回転方向	0x01（前）
        3	UInt8	モーターの速度指示値	0x64（100）
        4	UInt8	制御するモーターの ID	0x02（右）
        5	UInt8	モーターの回転方向	0x02（後）
        6	UInt8	モーターの速度指示値	0x14（20）
        7	UInt8	モーターの制御時間	0x0A（100 ミリ秒）
         */
        motor.front()

        /*
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
        */

    }

    override fun stop() {

        motor.stop()
        /*
        dispose = sessionManager.connection.flatMapSingle {

            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x00, 0x02, 0x01, 0x00))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
        */
    }

    override fun back() {
        motor.back()
        /*
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x02, 0x64, 0x02, 0x02, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })*/
    }

    override fun right() {
        motor.right()
        /*
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x02, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })*/
    }

    override fun left() {
        motor.left()
        /*
        dispose = sessionManager.connection.flatMapSingle {
            println("toio 1st front")
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x02, 0x64, 0x02, 0x01, 0x64))
            // it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x02, 0x01, 0x01, 0x64, 0x02, 0x01, 0x64, 0x10.toByte()))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })
        */
    }

    override fun busser() {
    }

    override fun turn() {
    }

    override fun disconnect() {
        Timber.d("toio disconnect")
        sessionManager.disconnect()
    }
}

