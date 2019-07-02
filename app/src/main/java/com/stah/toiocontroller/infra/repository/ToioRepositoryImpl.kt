package com.stah.toiocontroller.infra.repository

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toiocontroller.domain.ToioCube
import com.stah.toiocontroller.domain.repository.ToioRepository
import io.reactivex.disposables.Disposable
import timber.log.Timber


class ToioRepositoryImpl(val bleClient: RxBleClient) : ToioRepository {

    lateinit var dispose: Disposable
    val mSessionManager = BleSessionManager(bleClient, SchedulerProvider)
    lateinit var bleDevice: RxBleDevice

    override fun scan() {
        dispose = bleClient.scanBleDevices(
            ScanSettings.Builder().build()
        ).filter {
            Timber.d("toio name ${it.bleDevice.name}")
            Timber.d("toio uuid ${it.scanRecord.serviceUuids}")
            it.scanRecord.serviceUuids!!.contains(ParcelUuid(ToioCube.SERVICE_UUID))
        }
            // .timeout(15, TimeUnit.SECONDS)
            .subscribe({
                Timber.d("toio name ${it.bleDevice.name}")

                Timber.d("toio: mac ${it.bleDevice.macAddress}")

                mSessionManager.setMacAddress(it.bleDevice.macAddress)
                mSessionManager.setupConnection()
                bleDevice = it.bleDevice
                // connect = it.bleDevice.establishConnection(false)
                // connect()

                dispose.dispose()
            }, {
                Timber.e(it.localizedMessage)
            })
        // dispose.dispose()
    }


    override fun front() {

        dispose = mSessionManager.connection.flatMapSingle {
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x02, 0x14))
        }.subscribe({
            println("toio cube front ")
        }, {
            println(it)
        })

    }

    override fun back() {
    }

    override fun right() {
    }

    override fun left() {
    }

    override fun busser() {
    }

    override fun turn() {
    }

    override fun disconnect() {
        Timber.d("toio disconnect")
        mSessionManager.disconnect()
    }
}

