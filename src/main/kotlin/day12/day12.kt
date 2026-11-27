package day12

import getInput
import runLevels

const val TILE = "#"
const val EMPTY = "."
const val SIZE = 3

data class Shape(val key: String) {
    val tiles = key.chunked(1).count { it == TILE }
}

data class Region(val width: Int, val height: Int, val presents: List<Int>)

fun isPackingPossible(region: Region, shapes: List<Shape>): Boolean {
    val totalSpace = region.width * region.height
    val totalTiles = region.presents.mapIndexed { index, amount -> shapes[index].tiles * amount }.sum()
    return totalSpace >= totalTiles * 1.05 // some leeway allowed
}

fun solveLevel1(shapes: List<Shape>, regions: List<Region>): Long {
    return regions.count { isPackingPossible(it, shapes) }.toLong()
}

fun solveLevel2(): Long {
    return -1L
}

const val DAY = 12
const val USE_SAMPLE = false

fun main() {
    val text = getInput(DAY, USE_SAMPLE)

    val sections = text.split("\n\n")
    val shapes =
        sections.dropLast(1).map { section -> section.trim().lines().drop(1).joinToString("\n").let { Shape(it) } }
    val regions = sections.last().lines().filter { it.isNotBlank() }.map { region ->
        val (size, shapes) = region.split(": ")
        val (width, height) = size.split("x").map { it.toInt() }
        val presents = shapes.split(" ").map { it.toInt() }
        Region(width, height, presents)
    }
    runLevels(DAY, { solveLevel1(shapes, regions) }, { solveLevel2() }, 1)
}
