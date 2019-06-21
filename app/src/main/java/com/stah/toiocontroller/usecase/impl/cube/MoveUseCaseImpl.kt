package com.stah.toiocontroller.usecase.impl.cube

import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.domain.repository.ToioRepository
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import timber.log.Timber

class MoveUseCaseImpl(
    val reposiroty: ToioRepository
) : MoveUseCase {

    override fun scan() {
        Timber.d("usecase scan")
        reposiroty.scan()
    }

    override fun front(id: ToioCubeId) {
        Timber.d("usecase front")
        reposiroty.front()

    }

    override fun back() {
        Timber.d("usecase back")
        reposiroty.back()
    }
}