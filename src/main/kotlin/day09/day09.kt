package day09

import LVec2
import Vec2
import getInput
import plotVec2s
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

fun inArea(
    area: Pair<LVec2, LVec2>,
    target: LVec2,
): Boolean {
    val (corner1, corner2) = area
    val minX = minOf(corner1.x, corner2.x)
    val maxX = maxOf(corner1.x, corner2.x)
    val minY = minOf(corner1.y, corner2.y)
    val maxY = maxOf(corner1.y, corner2.y)

    return target.x in minX..maxX && target.y in minY..maxY
}

fun isValidRect(
    cornerIndices: Pair<Int, Int>,
    tiles: List<LVec2>,
): Boolean {
    val corner1 = tiles[cornerIndices.first]
    val corner2 = tiles[cornerIndices.second]

    val originalMinX = minOf(corner1.x, corner2.x)
    val originalMaxX = maxOf(corner1.x, corner2.x)
    val originalMinY = minOf(corner1.y, corner2.y)
    val originalMaxY = maxOf(corner1.y, corner2.y)

    val insetMinX = originalMinX + 1
    val insetMaxX = originalMaxX - 1
    val insetMinY = originalMinY + 1
    val insetMaxY = originalMaxY - 1

    if (insetMinX > insetMaxX || insetMinY > insetMaxY) {
        return tiles.isEmpty()
    }

    val insetAreaCorner1 = LVec2(insetMinX, insetMinY)
    val insetAreaCorner2 = LVec2(insetMaxX, insetMaxY)

    return !tiles.any { inArea(insetAreaCorner1 to insetAreaCorner2, it) }
}

fun solveLevel2(tiles: List<LVec2>): Long {
    var maxArea = 0L
    plotVec2s(tiles.map { Vec2(it.x.toInt(), it.y.toInt()) })
    for (i in tiles.indices) {
        val tile1 = tiles[i]
        for (j in tiles.indices.drop(i + 1)) {
            val tile2 = tiles[j]
            val d = tile1 - tile2
            val area = (abs(d.x) + 1) * (abs(d.y) + 1)
            if (area <= maxArea) continue
            if (isValidRect(i to j, tiles)) {
                println("new max $maxArea for $tile1 to $tile2")
                maxArea = area
            }
        }
    }
    return maxArea
}

const val DAY = 9
const val USE_SAMPLE = true

fun main() {
    println((10..5).toList())
    val text = getInput(DAY, USE_SAMPLE)
    val tiles =
        text
            .lines()
            .filter { it.isNotBlank() }
            .map { line ->
                val (x, y) = line.split(",").map(String::toLong)
                LVec2(x, y)
            }
    runLevels(DAY, { solveLevel1(tiles) }, { solveLevel2(tiles) }, 1)
}
