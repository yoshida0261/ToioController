package com.stah.toiocontroller.usecase.cube

import com.stah.toiocontroller.domain.ToioCubeId

interface MoveUseCase {
    fun scan()
    fun front(id: ToioCubeId)
    fun back()
}