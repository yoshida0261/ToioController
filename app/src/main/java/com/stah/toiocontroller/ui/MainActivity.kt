package com.stah.toiocontroller.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.stah.toiocontroller.R
import com.stah.toiocontroller.databinding.ActivityMainBinding
import com.stah.toiocontroller.domain.ToioCubeId
import com.stah.toiocontroller.usecase.cube.MoveUseCase
import org.koin.android.ext.android.inject
import permissions.dispatcher.*
import timber.log.Timber

@RuntimePermissions
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
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
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
                    manager.startUpdateFlowForResult(info, AppUpdateType.IMMEDIATE, this, REQUEST_CODE)

                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    // アップデートがない場合の処理。そのままアプリを起動するなど
                    println("UPDATE_NOT_AVAILABLE ${getVersionCode(this)}")
                }
            }
        }
    }

    /**
     * 連絡先の登録数をトーストで表示する。
     */
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showBleUse() {
        Toast.makeText(this@MainActivity, "ble ", Toast.LENGTH_SHORT).show()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onContactsDenied() {
        Toast.makeText(this, "「許可しない」が選択されました", Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForContacts(request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton("許可") { _, _ -> request.proceed() }
            .setNegativeButton("許可しない") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage("BLEを利用するため、位置情報を有効にする必要があります。")
            .show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onContactsNeverAskAgain() {
        Toast.makeText(this, "「今後表示しない」が選択されました", Toast.LENGTH_SHORT).show()
    }

    private fun popupSnackbarForCompleteUpdate() {
        // 更新完了のSnackbarを表示する
        Snackbar.make(findViewById(android.R.id.content), "An update has just been downloaded.", Snackbar.LENGTH_LONG)
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
        Timber.d("go scan")
        showBleUseWithPermissionCheck()
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
