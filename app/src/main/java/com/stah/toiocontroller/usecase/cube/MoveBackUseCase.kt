package com.stah.toiocontroller.usecase.cube

import com.stah.toiocontroller.domain.ToioCubeId


interface MoveBackUseCase {
    fun execute(id: ToioCubeId)
}