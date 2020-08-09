package com.stah.toiocontroller.usecase.cube

import com.stah.toiocontroller.domain.ToioCubeId

interface MoveUseCase {
    fun scan()
    fun front(id: ToioCubeId)
    fun back(id: ToioCubeId)
    fun left(id:ToioCubeId)
    fun right(id: ToioCubeId)
    fun disconnect(id: ToioCubeId)
}