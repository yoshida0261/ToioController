package com.stah.toiocontroller.usecase.cube

import com.stah.toiocontroller.domain.ToioCubeId


interface MoveRightUseCase {
    fun execute(id: ToioCubeId)

}