package com.stah.toiocontroller.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stah.toiocontroller.R
import com.stah.toiocontroller.ui.MainActivity
import kotlinx.android.synthetic.main.activity_connect.*
import permissions.dispatcher.*

@RuntimePermissions
class ConnectActivity : AppCompatActivity() {

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun scanToioCube() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationDeneed() {
        Toast.makeText(this, "位置情報を有効にしないとこのアプリは使用できません", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationNeverAskAgain() {
        Toast.makeText(this, "位置情報を有効にしないとこのアプリは使用できません", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)


        if (PermissionUtils.hasSelfPermissions(this, "android.permission.ACCESS_FINE_LOCATION")) {
            scanToioCube()
        }

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