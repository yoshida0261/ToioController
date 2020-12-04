package com.stah.toiocontroller.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import com.stah.toiocontroller.R
import com.stah.toiocontroller.databinding.ActivityMainBinding
import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity(), OnCubeControllListner {

    private val moveUseCase: MoveUseCase by inject()

    private lateinit var manager: AppUpdateManager
    private lateinit var listener: InstallStateUpdatedListener
    val REQUEST_CODE = 222


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                println("result_canceled")
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.handlers = this

        manager = AppUpdateManagerFactory.create(this)
        listener = InstallStateUpdatedListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()

                // メモリリーク対策のため、Listenerの登録を解除する
                manager.unregisterListener(listener)
            }
        }
        manager.registerListener(listener)
        println("update check start")
        manager.appUpdateInfo.addOnCompleteListener { task ->
            val info = task.result


            println("update check end")
            when (info.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    // 更新処理を行う
                    println("UPDATE_AVAILABLE ${getVersionCode(this)}")
                    manager.startUpdateFlowForResult(
                        info,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )

                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    // アップデートがない場合の処理。そのままアプリを起動するなど
                    println("UPDATE_NOT_AVAILABLE ${getVersionCode(this)}")
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()

        moveUseCase.disconnect(ToioCubeId(""))

    }

    private fun popupSnackbarForCompleteUpdate() {
        // 更新完了のSnackbarを表示する
        Snackbar.make(
            findViewById(android.R.id.content),
            "An update has just been downloaded.",
            Snackbar.LENGTH_LONG
        )
            .show();
    }


    fun getVersionCode(context: Context): Int {
        val pm = context.getPackageManager()
        var versionCode = 0
        try {
            val packageInfo = pm.getPackageInfo(context.getPackageName(), 0)
            versionCode = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionCode
    }

    override fun scan(view: View) {

        val reviewManager = ReviewManagerFactory.create(this)


        val request = reviewManager.requestReviewFlow()


        request.addOnSuccessListener { reviewInfo ->

            if (request.isSuccessful) {
                // We got the ReviewInfo object
                reviewManager.launchReviewFlow(this, reviewInfo)
                    .addOnSuccessListener {
                        // レビューの投稿が成功したときもキャンセルしたときも呼ばれる
                    }
            } else {
                // There was some problem, continue regardless of the result.
                Timber.e("error")
            }
        }
        //Timber.d("go scan")

        //moveUseCase.scan()
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
