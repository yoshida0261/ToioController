package com.stah.toio.lib.session

import android.content.res.Resources
import com.jakewharton.rx.ReplayingShare
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.Timeout
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Manages Bluetooth connection.
 * @property rxBleClient RxBleClient
 * @property mSchedulerProvider SchedulerProvider
 * @property eventStream CompositeDisposable
 * @property macAddress String?
 * @property disconnectTriggerSubject PublishSubject<Boolean>
 * @property _connection Observable<RxBleConnection>?
 * @property connection Observable<RxBleConnection>
 * @constructor
 */
class BleSessionManager(
    val rxBleClient: RxBleClient,
    val mSchedulerProvider: SchedulerProvider
) {

    private val BLE_CONNECT_OPERATION_TIMEOUT  = 15L
    private val eventStream: CompositeDisposable = CompositeDisposable()
    private var macAddress: String? = null
    private val disconnectTriggerSubject: PublishSubject<Boolean> = PublishSubject.create<Boolean>()
    private var _connection: Observable<RxBleConnection>? = null

    var connection: Observable<RxBleConnection>
        get() {
            return _connection ?: connectionFallback()
        }
        private set(value) {
            this._connection = value
        }

    /**
     * Fallback in case connection isn't valid.
     * @return Observable<RxBleConnection>
     */
    private fun connectionFallback(): Observable<RxBleConnection> {
        return Observable.error(NotConnectedException())
    }

    /**
     * Used to set mac address.
     * @param macAddress String
     */
    fun setMacAddress(macAddress: String) {
        this.macAddress = macAddress
    }

    /**
     * Configures connection. Called after `setMacAddress()`.
     * @throws NotFoundException May throw NotFoundException if mac address is not set.
     */
    fun setupConnection() {
        val address = this.macAddress ?: throw Resources.NotFoundException()

        val observable = rxBleClient.getBleDevice(address)
            .establishConnection(true, Timeout(BLE_CONNECT_OPERATION_TIMEOUT, TimeUnit.SECONDS))
            .takeUntil(disconnectTriggerSubject)
            .compose(ReplayingShare.instance())

        // Subscribe actions should be implemented even though they have no implementation.
        val disposable = observable
            .subscribeOn(SchedulerProvider.io())
            .retry(10)
            .subscribe(
                {},
                {},
                {}
            )
        eventStream.add(disposable)
        connection = observable
    }

    /**
     * Shuts down the connection and clears event stream.
     */
    private fun closeConnection() {
        disconnectTriggerSubject.onNext(true)
        eventStream.clear()
    }

    /**
     * Nullifies connection. Triggered by End User.
     */
    fun disconnect() {
        closeConnection()
        _connection = null
    }

    /**
     * Nullifies connection, but executes the given code before. Triggered by End User.
     */
    fun disconnectButFirst(extensionCode: () -> Unit) {
        extensionCode()
        disconnect()
    }
}