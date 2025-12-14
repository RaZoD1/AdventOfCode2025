package day09

import LVec2
import getInput
import runLevels
import kotlin.math.abs

fun solveLevel1(tiles: List<LVec2>): Long {
    var maxArea = 0L
    for (i in tiles.indices) {
        val tile1 = tiles[i]
        for (j in tiles.indices.drop(i + 1)) {
            val tile2 = tiles[j]
            val d = tile1 - tile2
            val area = (abs(d.x) + 1) * (abs(d.y) + 1)
            if (area > maxArea) {
                maxArea = area
            }
        }
    }
    return maxArea
}


fun solveLevel2(tiles: List<LVec2>): Long {
    return -1
}

const val DAY = 9
const val USE_SAMPLE = false

fun main() {
    val text = getInput(DAY, USE_SAMPLE)
    val tiles = text.lines().filter { it.isNotBlank() }
        .map { line ->
            val (x, y) = line.split(",").map(String::toLong);
            LVec2(x, y)
        }
    runLevels(DAY, { solveLevel1(tiles) }, { solveLevel2(tiles) })
}