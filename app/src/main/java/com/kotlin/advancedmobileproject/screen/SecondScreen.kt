package com.kotlin.advancedmobileproject.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import kotlin.math.sqrt

class SecondScreen(private val context: Context, game: Game) : Screen(game) {

    private val board = TicTacToeBoard()
    private val trophyIcon: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.reload)

    private var currentPlayer = CellState.X

    private var winner: CellState? = null
    private var winLine: Pair<Pair<Int, Int>, Pair<Int, Int>>? = null // ((startRow, startCol), (endRow, endCol))

    private var xWins = 0
    private var oWins = 0

    init {
        paint.color = ContextCompat.getColor(context, R.color.primary)
    }

    override fun update(et: Float) {
    }

    override fun draw() {
        canvas.drawColor(ContextCompat.getColor(context, R.color.primary))

        val correctHeight = (canvas.height - 450).toFloat()

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

                val metrics = paint.fontMetrics
                val offsetY = (metrics.ascent + metrics.descent) / 2

                when (cell.state) {
                    CellState.X -> {
                        paint.color = Color.WHITE
                        paint.textSize = 100f
                        canvas.drawText("x", centerX, centerY - offsetY, paint)
                    }
                    CellState.O -> {
                        paint.color = Color.BLACK
                        paint.textSize = 100f
                        canvas.drawText("o", centerX, centerY - offsetY, paint)
                    }
                    CellState.EMPTY -> { }
                }
            }
        }

        paint.color = Color.WHITE
        paint.strokeWidth = 10f

        canvas.drawRect(
            0f,
            correctHeight,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            paint
        )

        val iconSize = 80

        val scaledIcon = Bitmap.createScaledBitmap(trophyIcon, iconSize, iconSize, true)
        canvas.drawBitmap(scaledIcon, canvas.width / 2f - 40, canvas.height - 150f, paint)

        paint.color = Color.BLACK
        paint.textSize = 70f
        canvas.drawText("X: $xWins  |  O: $oWins", canvas.width / 2f, canvas.height - 250f, paint)

        if (winner != null) {

            if (winner == CellState.EMPTY) {
                paint.color = Color.YELLOW
                canvas.drawText("Empate!", canvas.width / 2f, canvas.height / 2f - 50f, paint)
            } else {
                winLine?.let { (start, end) ->
                    paint.strokeWidth = 10f

                    val cellWidth = canvas.width / 3f
                    val cellHeight = correctHeight / 3f

                    val startX = (start.second + 0.5f) * cellWidth
                    val startY = (start.first + 0.5f) * cellHeight
                    val endX = (end.second + 0.5f) * cellWidth
                    val endY = (end.first + 0.5f) * cellHeight

                    val dx = endX - startX
                    val dy = endY - startY
                    val length = sqrt((dx * dx + dy * dy).toDouble()).toFloat()
                    val extendFactor = 100f

                    val unitX = dx / length
                    val unitY = dy / length

                    val extendedStartX = startX - unitX * extendFactor
                    val extendedStartY = startY - unitY * extendFactor
                    val extendedEndX = endX + unitX * extendFactor
                    val extendedEndY = endY + unitY * extendFactor

                    paint.color = Color.YELLOW
                    canvas.drawLine(extendedStartX, extendedStartY, extendedEndX, extendedEndY, paint)
                }

            }
        }

    }


    override fun handleEvent(event: Int, x: Float, y: Float) {
        if (event == MotionEvent.ACTION_DOWN) {
            val cellWidth = canvas.width / 3f
            val cellHeight = (canvas.height - 450) / 3f

            val col = (x / cellWidth).toInt()
            val row = (y / cellHeight).toInt()

            if (y >= (canvas.height - 200)) {
                board.resetBoard()
                winner = null
                winLine = null
                return
            }

            if (winner != null || !board.board.indices.contains(row) || !board.board[row].indices.contains(col)) return

            if (board.board[row][col].state == CellState.EMPTY) {
                board.makeMove(row, col, currentPlayer)

                val (win, start, end) = board.checkWinWithCoords()
                if (win != null) {
                    winner = win
                    winLine = Pair(start, end)

                    when (winner) {
                        CellState.X -> xWins++
                        CellState.O -> oWins++
                        else -> {}
                    }

                }
                else if (board.isBoardFull()) {
                    winner = CellState.EMPTY
                } else {
                    currentPlayer = if (currentPlayer == CellState.X) CellState.O else CellState.X
                }
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