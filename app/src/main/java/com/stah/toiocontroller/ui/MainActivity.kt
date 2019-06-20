package com.stah.toiocontroller.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.stah.toiocontroller.R
import com.stah.toiocontroller.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() , OnCubeControllListner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding : PlainActivityBinding =
        //    DataBindingUtil.setContentView(this, R.layout.plain_activity)
        //setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.handlers = this

    }

    override fun moveFront(view: View) {
        Timber.d("go front")
    }

    override fun moveBack(view: View) {
        Timber.d("go back")

    }

    override fun moveLeft(view: View) {
        Timber.d("go left")
    }

    override fun moveRight(view: View) {
        Timber.d("go right")
    }


}
