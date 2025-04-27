package com.kotlin.advancedmobileproject

class TicTacToeBoard {
    val board: Array<Array<Cell>> = Array(3) { row ->
        Array(3) { col -> Cell(row, col) }
    }

    fun resetBoard() {
        for (row in board) {
            for (cell in row) {
                cell.state = CellState.EMPTY
            }
        }
    }

    fun makeMove(row: Int, col: Int, player: CellState) {
        if (board[row][col].state == CellState.EMPTY) {
            board[row][col].state = player
        }
    }
}
