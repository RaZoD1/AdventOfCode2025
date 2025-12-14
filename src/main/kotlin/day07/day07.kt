package day07

import Grid
import getInput
import parseGrid
import runLevels

const val EMPTY = '.'
const val START = 'S'
const val SPLITTER = '^'

fun solveLevel1(grid: Grid): Long {
    var splits = 0L
    var beams = mutableSetOf<Int>()
    for (row in grid) {
        val newBeams = mutableSetOf<Int>()
        row.indexOf(START).let { if (it != -1) newBeams.add(it) }
        beams.forEach { beam ->
            if (row[beam] == EMPTY) {
                newBeams.add(beam)
            } else if (row[beam] == SPLITTER) {
                splits++
                newBeams.add(beam - 1)
                newBeams.add(beam + 1)
            }
        }
        beams = newBeams
    }
    return splits
}


fun solveLevel2(grid: Grid): Long {
    // Map<ColumnIndex to AmountOfTimelines>
    val beams = mutableMapOf<Int, Long>()
    fun setOrAdd(key: Int, value: Long) {
        beams[key] = (beams[key] ?: 0L) + value
    }

    for (row in grid) {
        row.indexOf(START).let { if (it != -1) beams[it] = 1L }
        beams.toMap().forEach { (index, timelines) ->
            if (row[index] == SPLITTER) {
                beams.remove(index)
                setOrAdd(index - 1, timelines)
                setOrAdd(index + 1, timelines)
            }
        }
    }
    return beams.values.sum()
}

const val DAY = 7


fun main() {
    val text = getInput(DAY)
    val grid = parseGrid(text)
    runLevels(DAY, { solveLevel1(grid) }, { solveLevel2(grid) })
}