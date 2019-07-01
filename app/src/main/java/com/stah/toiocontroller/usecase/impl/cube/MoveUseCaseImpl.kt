package com.stah.toiocontroller.usecase.impl.cube

import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.domain.repository.ToioRepository
import com.stah.toiocontroller.usecase.cube.DisconnectUseCase
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import timber.log.Timber

class MoveUseCaseImpl(
    val reposiroty: ToioRepository
) : MoveUseCase, DisconnectUseCase {
    override fun back(id: ToioCubeId) {
        reposiroty.disconnect()
    }

    override fun left(id: ToioCubeId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun right(id: ToioCubeId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scan() {
        Timber.d("usecase scan")
        reposiroty.scan()
    }

    override fun front(id: ToioCubeId) {
        Timber.d("usecase front")
        reposiroty.front()

    }

    override fun disconnect(id: ToioCubeId) {
        reposiroty.disconnect()
    }


}