package com.stah.toiocontroller.usecase.impl.cube

import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.domain.repository.ToioRepository
import com.stah.toiocontroller.usecase.cube.DisconnectUseCase
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import timber.log.Timber

class MoveUseCaseImpl(
    val reposiroty: ToioRepository
) : MoveUseCase, DisconnectUseCase {

    var moveFront = true
    var moveRight = true
    var moveLeft = true
    var moveBack = true
    var scanState = false


    override fun back(id: ToioCubeId) {
        if(moveBack){
            reposiroty.back()
        }else{
            reposiroty.stop()
        }

        moveBack = !moveBack
    }

    override fun left(id: ToioCubeId) {
        if(moveLeft){
            reposiroty.left()
        }else{
            reposiroty.stop()
        }
        moveLeft = !moveLeft
    }

    override fun right(id: ToioCubeId) {

        if(moveRight){
            reposiroty.right()
        }else{
            reposiroty.stop()
        }
        moveRight = !moveRight


    }


    override fun scan() {
        Timber.d("usecase scan")
        if(!scanState) {
            reposiroty.scan()
        }else{
            reposiroty.disconnect()
        }
    }


    override fun front(id: ToioCubeId) {
        Timber.d("usecase front")


        if (moveFront) {
            reposiroty.front()
        } else {
            reposiroty.stop()
        }

        moveFront = !moveFront


    }

    override fun disconnect(id: ToioCubeId) {
        reposiroty.disconnect()
    }


}