package com.stah.toiocontroller.infra.repository

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toio.lib.Battery
import com.stah.toio.lib.Motor
import com.stah.toio.lib.ToioCube
import com.stah.toio.lib.session.BleSessionManager
import com.stah.toio.lib.session.SchedulerProvider
import com.stah.toiocontroller.domain.repository.ToioRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class ToioRepositoryImpl(val bleClient: RxBleClient) : ToioRepository {

    val dispose = CompositeDisposable()
    private val sessionManager = BleSessionManager(bleClient, SchedulerProvider)
    private val motor = Motor(sessionManager)

    override fun scan() {
        dispose.add(bleClient.scanBleDevices(
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
                //dispose.dispose()
            }, {
                Timber.e(it.localizedMessage)
                //throw Exception()
            })
        )
    }

    override fun battery(): Observable<ByteArray> {
        val battery = Battery(sessionManager)
        return battery.getBattery()
    }

    fun led() {
        dispose.add(sessionManager.connection.flatMapSingle {

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
        }))
    }


    override fun front() {

        motor.front()


    }

    override fun stop() {

        motor.stop()
    }

    override fun back() {
        motor.back()
    }

    override fun right() {
        motor.right()
    }

    override fun left() {
        motor.left()
    }

    override fun busser() {
    }


    override fun disconnect() {
        Timber.d("toio disconnect")
        sessionManager.disconnect()
        dispose.dispose()
    }
}

