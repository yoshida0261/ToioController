package com.stah.toiocontroller.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.stah.toiocontroller.R
import com.stah.toiocontroller.activity.scan.ScanViewModel
import com.stah.toiocontroller.databinding.ActivityScanBinding
import com.stah.toiocontroller.ui.MainActivity

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer { value -> value?.let { observer(it) } })
}

class ScanActivity : AppCompatActivity() {

    lateinit var viewModel: ScanViewModel

    //private lateinit var binding: ActivityEditProfileBinding
    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        viewModel = ScanViewModel(application)
        //  viewModel.scanToioCube()

        viewModel.toioStart.observeNonNull(this) {
            viewModel.scanTerminate()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        binding =
            DataBindingUtil.setContentView<ActivityScanBinding>(this, R.layout.activity_scan).also {
                it.viewModel = viewModel
            }

        // ここでプログレス
    }

    override fun onStop() {
        super.onStop()

    }


}