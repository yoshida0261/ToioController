package com.stah.toiocontroller.usecase.impl.cube

import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.usecase.cube.MoveBackUseCase
import timber.log.Timber


class MoveBackUseCaseImpl : MoveBackUseCase {
    override fun execute(id: ToioCubeId) {
        Timber.d("usecase back")


    }

}