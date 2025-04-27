package com.kotlin.advancedmobileproject.screen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.kotlin.advancedmobileproject.CellState
import com.kotlin.advancedmobileproject.Game
import com.kotlin.advancedmobileproject.R
import com.kotlin.advancedmobileproject.Screen
import com.kotlin.advancedmobileproject.TicTacToeBoard

class SecondScreen(private val context: Context, game: Game) : Screen(game) {

    private val board = TicTacToeBoard()
    private var currentPlayer = CellState.X

    init {
        paint.color = ContextCompat.getColor(context, R.color.primary)
    }

    override fun update(et: Float) {
    }

    override fun draw() {
        canvas.drawColor(ContextCompat.getColor(context, R.color.primary))

        val correctHeight = (canvas.height - 200).toFloat()

        val cellWidth = canvas.width / 3f
        val cellHeight = correctHeight / 3f


        paint.color = Color.WHITE
        paint.strokeWidth = 10f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawLine(cellWidth, 0f, cellWidth, correctHeight, paint)
        canvas.drawLine(cellWidth * 2, 0f, cellWidth * 2, correctHeight, paint)
        canvas.drawLine(0f, cellHeight, canvas.width.toFloat(), cellHeight, paint)
        canvas.drawLine(0f, cellHeight * 2, canvas.width.toFloat(), cellHeight * 2, paint)

        for (row in board.board) {
            for (cell in row) {
                val centerX = (cell.col + 0.5f) * cellWidth
                val centerY = (cell.row + 0.5f) * cellHeight
                when (cell.state) {
                    CellState.X -> {
                        paint.color = Color.BLUE
                        paint.textSize = 100f
                        canvas.drawText("x", centerX, centerY, paint)
                    }
                    CellState.O -> {
                        paint.color = Color.BLACK
                        paint.textSize = 100f
                        canvas.drawText("o", centerX, centerY, paint)
                    }
                    CellState.EMPTY -> { }
                }
            }
        }

        paint.color = Color.BLUE
        paint.strokeWidth = 10f

        canvas.drawRect(
            0f,
            correctHeight,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            paint
        )

        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas.drawText("Restart", canvas.width / 2f, canvas.height - 100f, paint)
    }


    override fun handleEvent(event: Int, x: Float, y: Float) {
        if (event == MotionEvent.ACTION_DOWN) {
            val cellWidth = canvas.width / 3f
            val cellHeight = (canvas.height - 200) / 3f

            val col = (x / cellWidth).toInt()
            val row = (y / cellHeight).toInt()

            if(y >= (canvas.height - 200)) {
                board.resetBoard()
                return
            }

            if (board.board[row][col].state == CellState.EMPTY) {
                board.makeMove(row, col, currentPlayer)

                currentPlayer = if (currentPlayer == CellState.X) CellState.O else CellState.X
            }
        }
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onBackPressed() {
        game.actualScreen = FirstScreen(context, game)
    }

}