package com.stah.toiocontroller.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.stah.toiocontroller.R
import com.stah.toiocontroller.databinding.ActivityMainBinding
import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnCubeControllListner {

    private val moveUseCase: MoveUseCase by inject()

    val REQUEST_CODE = 222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.handlers = this

        val manager = AppUpdateManagerFactory.create(this)
        manager.appUpdateInfo.addOnCompleteListener { task ->
            val info = task.result

            when (info.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    // 更新処理を行う
                    println("UPDATE_AVAILABLE")
                    manager.startUpdateFlowForResult(info, AppUpdateType.IMMEDIATE, this, REQUEST_CODE)

                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    // アップデートがない場合の処理。そのままアプリを起動するなど
                    println("UPDATE_NOT_AVAILABLE")
                }
            }
        }
    }

    override fun scan(view: View) {
        Timber.d("go scan")
        moveUseCase.scan()
    }

    override fun moveFront(view: View) {
        Timber.d("go front")
        moveUseCase.front(ToioCubeId("test"))
    }

    override fun moveBack(view: View) {
        Timber.d("go back")
        moveUseCase.back(ToioCubeId("id"))

    }

    override fun moveLeft(view: View) {
        Timber.d("go left")
        moveUseCase.left(ToioCubeId("id"))
    }

    override fun moveRight(view: View) {
        Timber.d("go right")
        moveUseCase.right(ToioCubeId("id"))

    }
}
