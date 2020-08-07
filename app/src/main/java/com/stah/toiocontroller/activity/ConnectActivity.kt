package com.stah.toiocontroller.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stah.toiocontroller.R
import kotlinx.android.synthetic.main.activity_connect.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class ConnectActivity : AppCompatActivity() {

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun scanToioCube() {

    }


    /*
        この画面を使って接続を許可する
        次の画面でスキャン
        これはメインのUIを覆うようにしてあげたい
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        connectButton.setOnClickListener {
            scanToioCubeWithPermissionCheck()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }
}