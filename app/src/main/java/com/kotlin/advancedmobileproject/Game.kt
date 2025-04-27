package com.kotlin.advancedmobileproject

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.core.graphics.createBitmap
import kotlin.math.abs

class Game(private val context: Context) {

    var buffer: Bitmap
    var render: Render
    var actualScreen: Screen? = null

    private var nWidth = 0f
    private var nHeight = 0f
    private var horizontalDist = 0f
    private var verticalDist = 0f
    private var scaleX = 0f
    private var scaleY = 0f

    init {
        val isLandscape = context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val bufferWidth = if(isLandscape) 1800f else 1080f
        val bufferHeight = if(isLandscape) 1080f else 1800f

        buffer = createBitmap(bufferWidth.toInt(), bufferHeight.toInt())

        val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

        val aspectBuffer = bufferWidth / bufferHeight
        val aspectScreen = screenWidth / screenHeight

        if(aspectBuffer > aspectScreen || abs(aspectScreen - aspectBuffer) < 0.1) {
            this.nWidth = screenWidth
            val factorAsp = aspectScreen / aspectBuffer
            this.nHeight = screenHeight * factorAsp
            this.verticalDist = (screenHeight - nHeight) / 2
        } else {
            this.nHeight = screenHeight
            this.nWidth = nHeight * aspectBuffer
            this.horizontalDist = (screenWidth - nHeight) / 2
        }

        scaleX = nWidth / bufferWidth
        scaleY = nHeight / bufferHeight

        render = Render(context, buffer)
        render.run { setOnTouchListener(this) }
    }

    inner class Render(context: Context, private val buffer: Bitmap) : View(context), OnTouchListener {

        private var startTime = System.nanoTime()
        private var paint = Paint()
        private var src: Rect = Rect()
        private var dst: Rect = Rect()

        init {
            paint.isAntiAlias = true
            paint.isFilterBitmap = true
            paint.isDither = true
            src.set(0, 0, buffer.width, buffer.height)
            dst.set(
                horizontalDist.toInt(),
                verticalDist.toInt(),
                (nWidth + horizontalDist).toInt(),
                (nHeight + verticalDist).toInt()
            )
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val elapsedTime = (System.nanoTime() - startTime) / 1000000f
            startTime = System.nanoTime()

            actualScreen?.update(elapsedTime)
            actualScreen?.draw()

            canvas.also { c ->
                c.drawColor(Color.BLACK)
                c.drawBitmap(buffer, src, dst, paint)
            }

            invalidate()
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {

            event?.let { e ->
                val x = (e.x - horizontalDist) / scaleX
                val y = (e.y - verticalDist) / scaleY

                actualScreen?.handleEvent(e.action, x, y)
            }

            return true
        }
    }

    fun onPause() {
        actualScreen?.onPause()
    }

    fun onResume() {
        actualScreen?.onResume()
    }

    fun onBackPressed() {
        actualScreen?.onBackPressed()
    }
}