package day04

import Grid
import Vec2
import at
import getInput
import inGrid
import parseGrid
import runLevels

const val ROLL: Char = '@';

fun solveLevel1(grid: Grid): Long {
    val dirs =
        listOf(Vec2.UP, Vec2.DOWN, Vec2.LEFT, Vec2.RIGHT, Vec2.UP_LEFT, Vec2.DOWN_RIGHT, Vec2.UP_RIGHT, Vec2.DOWN_LEFT)

    var accessibleRolls = 0L
    for ((rowIdx, row) in grid.withIndex()) {
        for ((colIdx, c) in row.withIndex()) {
            if (c != ROLL) continue
            val pos = Vec2(colIdx, rowIdx)
            val neighbours = dirs.map { pos + it }.filter { grid.inGrid(it) }.map { grid.at(it) }
            val accessible = neighbours.count { it == ROLL } < 4
            if (accessible) accessibleRolls++
        }
    }

    return accessibleRolls
}

fun solveLevel2(grid: Grid): Long {
    return -1
}

const val DAY = 4

fun main() {
    val text = getInput(DAY)
    val grid = parseGrid(text);
    runLevels(DAY, { solveLevel1(grid) }, { solveLevel2(grid) })
}