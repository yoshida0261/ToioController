package com.stah.toiocontroller.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.stah.toiocontroller.R
import com.stah.toiocontroller.databinding.ActivityMainBinding
import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnCubeControllListner {

    private val moveUseCase: MoveUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.handlers = this
    }

    override fun scan(view: View) {
        Timber.d("go scan")
        moveUseCase.scan()
    }

    override fun moveFront(view: View) {
        Timber.d("go front")
    }

    override fun moveBack(view: View) {
        Timber.d("go back")
        moveUseCase.execute(ToioCubeId("test"))

    }

    override fun moveLeft(view: View) {
        Timber.d("go left")
    }

    override fun moveRight(view: View) {
        Timber.d("go right")
    }


}
