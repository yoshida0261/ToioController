package com.stah.toiocontroller.domain.repository


interface ToioRepository {
    fun connect()
    fun front()
    fun back()
    fun right()
    fun left()
    fun busser()
    fun turn()
}