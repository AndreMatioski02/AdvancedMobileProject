package com.kotlin.advancedmobileproject.screen

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.kotlin.advancedmobileproject.Fonts
import com.kotlin.advancedmobileproject.Game
import com.kotlin.advancedmobileproject.R
import com.kotlin.advancedmobileproject.Screen

class FirstScreen(private val context: Context, game: Game) : Screen(game) {

    init {
        paint.typeface = Fonts.chalkboard
        paint.textSize = 100f
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true
        paint.textSkewX = 0.1f
    }

    override fun update(et: Float) {
    }

    override fun draw() {
        canvas.drawColor(Color.BLACK)

        paint.color = ContextCompat.getColor(context, R.color.primary)

        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

        paint.color = Color.WHITE
        paint.textSize = 100f
        canvas.drawText("Toque para iniciar!!!", canvas.width / 2f, canvas.height / 2f, paint)

        paint.textSize = 30f
        canvas.drawText("Copyright Advanced Mobile", canvas.width / 2f, canvas.height - 60f, paint)
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
        if(event == MotionEvent.ACTION_DOWN) {
            game.actualScreen = SecondScreen(context, game)
        }
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onBackPressed() {
    }

}