package com.stah.toiocontroller.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stah.toiocontroller.R
import com.stah.toiocontroller.activity.scan.ScanViewModel
import com.stah.toiocontroller.cube.DefaultToioCubeScan

class ScanActivity : AppCompatActivity() {

    lateinit var viewModel: ScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        viewModel = ScanViewModel(DefaultToioCubeScan())
        //  viewModel.scanToioCube()

        // ここでプログレス
    }

    override fun onStop() {
        super.onStop()
        viewModel.scanTerminate()
    }


}