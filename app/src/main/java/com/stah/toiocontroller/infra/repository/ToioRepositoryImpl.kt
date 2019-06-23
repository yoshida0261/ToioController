package com.stah.toiocontroller.infra.repository

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanSettings
import com.stah.toiocontroller.domain.ToioCube
import com.stah.toiocontroller.domain.repository.ToioRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber


class ToioRepositoryImpl(val bleClient: RxBleClient) : ToioRepository {

    lateinit var dispose: Disposable


    lateinit var bleDevice: RxBleDevice
    lateinit var connect: Observable<RxBleConnection>
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
                bleDevice = it.bleDevice
                // connect = it.bleDevice.establishConnection(false)
                connect()

                dispose.dispose()
            }, {
                Timber.e("error")
            })
    }

    /*
            val bda = device.bluetoothDevice.address
        mVerDisposable = device.establishConnection(false)
            .flatMapSingle { rxbleCon ->
                connection = rxbleCon

                // read FW version
                rxbleCon.setupNotification(
                    UUID.fromString(ToioUuid.TOIO_CORE_CUBE_CONFIG_CHAR_UUID),
                    NotificationSetupMode.DEFAULT
                )
                rxbleCon.writeCharacteristic(
                    UUID.fromString(ToioUuid.TOIO_CORE_CUBE_CONFIG_CHAR_UUID),
                    byteArrayOf(ToioConfiguration.FW_VERSION.toByte(), 0)
                )
            }.flatMapSingle {
                // wait notify timing
                Thread.sleep(100)
                connection.readCharacteristic(UUID.fromString(ToioUuid.TOIO_CORE_CUBE_CONFIG_CHAR_UUID))
            }
            .flatMapSingle { v ->
                // read battery
                Timber.d("raw ${v.joinToString("") { "${it.toChar()}" }}")
                mFWversion = String(v, Charset.forName("US-ASCII")).substring(2)

                Timber.d("FWversion $mFWversion : raw ${v.joinToString("") { "${it.toChar()}" }}")
                connection.readCharacteristic(UUID.fromString(ToioUuid.TOIO_CORE_CUBE_BATTERY_CHAR_UUID))
            }.timeout(15, TimeUnit.SECONDS)
            .subscribe(
                { characteristicValue ->
                    Timber.d("getBattery : ${characteristicValue[0]}")
                    mVerDisposable?.dispose()

                    mListener.setBatteryAndFwVersion(characteristicValue[0].toString(), mFWversion, bda)
                }, {
                    Timber.e(it)
                    mListener.showSearchAgain()
                    mVerDisposable?.dispose()
                }
            )

     */

    override fun connect() {
        connect = bleDevice.establishConnection(false)
    }

    override fun front() {
        println("ble front")
        dispose = connect.flatMapSingle {
            // it.readCharacteristic(ToioCube.BATTERY_UUID)
            it.writeCharacteristic(ToioCube.MOTOR_UUID, byteArrayOf(0x01, 0x01, 0x01, 0x64, 0x02, 0x02, 0x14))
        }.subscribe() {
            // println("toio raw ${it[0]}")
            // dispose.dispose()
        }

    }

    override fun back() {
        dispose.dispose()
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

    }
}

