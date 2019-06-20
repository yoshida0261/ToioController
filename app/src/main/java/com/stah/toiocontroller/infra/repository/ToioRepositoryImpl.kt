package com.stah.toiocontroller.infra.repository

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toiocontroller.domain.ToioCube
import com.stah.toiocontroller.domain.repository.ToioRepository
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


class ToioRepositoryImpl(val bleClient: RxBleClient) : ToioRepository {

    lateinit var dispose: Disposable
    override fun scan() {
        dispose = bleClient.scanBleDevices(
            ScanSettings.Builder().build()
        ).filter {
            it.scanRecord.serviceUuids?.contains(
                ParcelUuid(UUID.fromString(ToioCube.TOIO_CORE_CUBE_SERVICE_UUID))
            )!!
        }
            .timeout(15, TimeUnit.SECONDS)
            .subscribe({
                Timber.d(it.bleDevice.macAddress)
                dispose.dispose()

            }, {
                Timber.e("error")
            })


    }

    override fun connect() {

    }

    override fun front() {

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
}