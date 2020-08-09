package com.stah.toiocontroller.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.stah.toiocontroller.R
import com.stah.toiocontroller.activity.scan.ScanViewModel
import com.stah.toiocontroller.cube.DefaultToioCubeScan
import com.stah.toiocontroller.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    lateinit var viewModel: ScanViewModel

    //private lateinit var binding: ActivityEditProfileBinding
    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        viewModel = ScanViewModel(DefaultToioCubeScan())
        //  viewModel.scanToioCube()

        binding =
            DataBindingUtil.setContentView<ActivityScanBinding>(this, R.layout.activity_scan).also {
                it.viewModel = viewModel
            }

        // ここでプログレス
    }

    override fun onStop() {
        super.onStop()
        viewModel.scanTerminate()
    }


}