package com.stah.toiocontroller.cube

import android.os.ParcelUuid
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toio.lib.ToioCube
import com.stah.toio.lib.session.BleSessionManager
import com.stah.toio.lib.session.SchedulerProvider
import com.stah.toiocontroller.MyApp
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface ToioCubeScan {

    fun start()

    fun stop()

}

class DefaultToioCubeScan() : ToioCubeScan {

    val dispose = CompositeDisposable()
    val bleClient = MyApp.ToioController.rxBleClient
    private val sessionManager = BleSessionManager(bleClient, SchedulerProvider)


    override fun start() {
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

    private fun led() {
        dispose.add(sessionManager.connection.flatMapSingle {
            it.writeCharacteristic(
                ToioCube.LIGHT_UUID, byteArrayOf(
                    0x04, 0x00, 0x01, 0x1E, 0x01, 0x01,
                    0xff.toByte(), 0xff.toByte(), 0xff.toByte()
                )
            )
        }.subscribe({
            Timber.d("light $it")
        }, {
            Timber.d(it)
        }))
    }

    override fun stop() {
        dispose.dispose()
    }

}