package com.stah.toiocontroller.usecase.cube

import com.stah.toiocontroller.domain.ToioCubeId


interface MoveLeftUseCase {
    fun execute(id: ToioCubeId)

}