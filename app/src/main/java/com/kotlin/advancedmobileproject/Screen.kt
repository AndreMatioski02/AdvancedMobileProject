package com.kotlin.advancedmobileproject

import android.graphics.Canvas
import android.graphics.Paint

abstract class Screen(val game: Game) {

    protected val canvas = Canvas(game.buffer)
    protected val paint = Paint()

    abstract fun update(et: Float)
    abstract fun draw()
    abstract fun handleEvent(event: Int, x: Float, y: Float)
    abstract fun onPause()
    abstract fun onResume()
    abstract fun onBackPressed()
}