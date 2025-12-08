package day04

import Grid
import Vec2
import at
import getInput
import inGrid
import map
import parseGrid
import runLevels

const val ROLL: Char = '@';
const val EMPTY: Char = '.';

val DIRS =
    listOf(Vec2.UP, Vec2.DOWN, Vec2.LEFT, Vec2.RIGHT, Vec2.UP_LEFT, Vec2.DOWN_RIGHT, Vec2.UP_RIGHT, Vec2.DOWN_LEFT)

fun solveLevel1(grid: Grid): Long {
    var accessibleRolls = 0L
    for ((rowIdx, row) in grid.withIndex()) {
        for ((colIdx, c) in row.withIndex()) {
            if (c != ROLL) continue
            val pos = Vec2(colIdx, rowIdx)
            val neighbours = grid.neighbours(pos).count { grid.at(it) == ROLL }
            val accessible = neighbours < 4
            if (accessible) accessibleRolls++
        }
    }

    return accessibleRolls
}

fun Grid.neighbours(pos: Vec2): Set<Vec2> = DIRS.map { pos + it }.filter { this.inGrid(it) }.toSet()

fun solveLevel2(grid: Grid): Long {

    var newGrid = grid;
    var totalRemoved = 0L
    do {
        var removed: Int = 0
        newGrid = newGrid.map { pos, c ->
            if (c == ROLL && newGrid.neighbours(pos).count { newGrid.at(it) == ROLL } < 4)
                EMPTY.also { removed++ }
            else c
        }
//        println(newGrid.joinToString("\n"))
//        println("Removed $removed")
        totalRemoved += removed
    } while (removed > 0)
    return totalRemoved
}

const val DAY = 4

fun main() {
    val text = getInput(DAY)
    val grid = parseGrid(text);
    runLevels(DAY, { solveLevel1(grid) }, { solveLevel2(grid) })
}