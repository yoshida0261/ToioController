package com.stah.toiocontroller.infra.repository

import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/*
class BluetoothService(
    val bluetoothClient: RxBleClient,
    val bluetoothScanSettings: ScanSettings,
    val mSchedulerProvider: SchedulerProvider,
    val bleSessionManager: BleSessionManager
) : IBluetoothService {

    private fun observeConnection(device: RxBleDevice): Observable<ConnectionState> {
        return device
            .observeConnectionStateChanges()
            .throttleLatest(500, TimeUnit.MILLISECONDS)
            .subscribeOn(mSchedulerProvider.io())
            .map { it.mapToConnectionState() }
    }

    override fun connectToDevice(macAddress: String): Observable<ConnectionState> {
        val device = bluetoothClient.getBleDevice(macAddress)
        bleSessionManager.setMacAddress(macAddress)
        bleSessionManager.setupConnection()
        return observeConnection(device)
    }

    override fun getDeviceShadow(): Single<HelixDeviceShadow> {

        return bleSessionManager.connection
            .subscribeOn(mSchedulerProvider.io())
            .firstOrError()
            .flatMap { it.readCharacteristic(MY_UUID) }
            .map {
                it.mapToMyResponse()
            }
            .doOnError { it.mapBluetoothGattOperationExceptions() }
    }

    override fun disconnect() {

        var disposable: Disposable? = null

        disposable = bleSessionManager.connection
            .subscribeOn(mSchedulerProvider.io())
            .firstOrError()
            .flatMap {
                it.writeCharacteristic(SLEEP_UUID, putHelixToSleep())
            }
            .ignoreElement()
            .onErrorComplete()
            .doFinally {

                disposable?.dispose()
                otaUpdateDisposables.clear()
                bleSessionManager.disconnect()
            }
            .subscribe()
    }*/