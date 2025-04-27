package com.kotlin.advancedmobileproject

enum class CellState {
    EMPTY,
    X,
    O
}


data class Cell(val row: Int, val col: Int, var state: CellState = CellState.EMPTY)
