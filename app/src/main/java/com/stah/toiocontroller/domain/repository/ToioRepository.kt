package com.stah.toiocontroller.domain.repository

import io.reactivex.Observable

interface ToioRepository {
    fun scan()
    fun disconnect()
    fun stop()
    fun front()
    fun back()
    fun right()
    fun left()
    fun busser()
    fun battery(): Observable<ByteArray>
}