package com.stah.toiocontroller.ui

import android.view.View


interface OnCubeControllListner {
    fun moveFront(view: View)
    fun moveBack(view: View)
    fun moveLeft(view: View)
    fun moveRight(view: View)
}