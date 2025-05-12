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

    fun checkWin(): CellState? {
        for (i in 0..2) {
            if (board[i][0].state != CellState.EMPTY &&
                board[i][0].state == board[i][1].state &&
                board[i][1].state == board[i][2].state
            ) {
                return board[i][0].state
            }

            if (board[0][i].state != CellState.EMPTY &&
                board[0][i].state == board[1][i].state &&
                board[1][i].state == board[2][i].state
            ) {
                return board[0][i].state
            }
        }

        if (board[0][0].state != CellState.EMPTY &&
            board[0][0].state == board[1][1].state &&
            board[1][1].state == board[2][2].state
        ) {
            return board[0][0].state
        }

        if (board[0][2].state != CellState.EMPTY &&
            board[0][2].state == board[1][1].state &&
            board[1][1].state == board[2][0].state
        ) {
            return board[0][2].state
        }

        return null
    }

    fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.state != CellState.EMPTY } }
    }

    fun checkWinWithCoords(): Triple<CellState?, Pair<Int, Int>, Pair<Int, Int>> {
        for (i in 0..2) {
            if (board[i][0].state != CellState.EMPTY &&
                board[i][0].state == board[i][1].state &&
                board[i][1].state == board[i][2].state
            ) {
                return Triple(board[i][0].state, Pair(i, 0), Pair(i, 2))
            }

            if (board[0][i].state != CellState.EMPTY &&
                board[0][i].state == board[1][i].state &&
                board[1][i].state == board[2][i].state
            ) {
                return Triple(board[0][i].state, Pair(0, i), Pair(2, i))
            }
        }

        if (board[0][0].state != CellState.EMPTY &&
            board[0][0].state == board[1][1].state &&
            board[1][1].state == board[2][2].state
        ) {
            return Triple(board[0][0].state, Pair(0, 0), Pair(2, 2))
        }

        if (board[0][2].state != CellState.EMPTY &&
            board[0][2].state == board[1][1].state &&
            board[1][1].state == board[2][0].state
        ) {
            return Triple(board[0][2].state, Pair(0, 2), Pair(2, 0))
        }

        return Triple(null, Pair(0,0), Pair(0,0))
    }


}
