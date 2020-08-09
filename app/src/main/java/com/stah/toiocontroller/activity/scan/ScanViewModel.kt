package com.stah.toiocontroller.activity.scan

import android.app.Application
import android.os.ParcelUuid
import androidx.lifecycle.LifecycleObserver
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toio.lib.ToioCube
import com.stah.toio.lib.session.BleSessionManager
import com.stah.toio.lib.session.SchedulerProvider
import com.stah.toiocontroller.MyApp
import com.stah.toiocontroller.activity.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ScanViewModel internal constructor(
    application: Application
) : LifecycleObserver {

    val toioStart = SingleLiveEvent<Unit>()


    fun scanToioCube() {
        start()
    }

    fun ledStart() {

    }

    fun scanTerminate() {
        stop()
    }

    val dispose = CompositeDisposable()
    val bleClient = MyApp.ToioController.rxBleClient
    private val sessionManager = BleSessionManager(bleClient, SchedulerProvider)


    fun start() {
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
                toioStart.value = Unit
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

    fun stop() {
        dispose.dispose()
    }


}