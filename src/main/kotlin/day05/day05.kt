package day05

import getInput
import runLevels

fun solveLevel1(ranges: List<LongRange>, ids: List<Long>): Long {
    return ids.count { id -> ranges.any { range -> range.contains(id) } }.toLong()
}


fun solveLevel2(ranges: List<LongRange>, ids: List<Long>): Long {
    return -1
}

const val DAY = 5

fun main() {
    val text = getInput(DAY)
    val (rawRanges, rawIds) = text
        .replace("\r", "")
        .split("\n\n")
        .map { it.lines().filter(String::isNotBlank) }

    val ranges = rawRanges.map { val (start, end) = it.split("-").map(String::toLong); start..end }
    val ids = rawIds.map { it.toLong() }
    runLevels(DAY, { solveLevel1(ranges, ids) }, { solveLevel2(ranges, ids) })
}