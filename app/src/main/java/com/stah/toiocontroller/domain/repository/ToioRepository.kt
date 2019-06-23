package com.stah.toiocontroller.domain.repository

interface ToioRepository {
    fun scan()
    fun connect()
    fun disconnect()
    fun front()
    fun back()
    fun right()
    fun left()
    fun busser()
    fun turn()
}