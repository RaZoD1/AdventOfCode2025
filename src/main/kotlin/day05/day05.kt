package day05

import getInput
import runLevels
import kotlin.math.min

fun solveLevel1(ranges: List<LongRange>, ids: List<Long>): Long {
    return ids.count { id -> ranges.any { range -> range.contains(id) } }.toLong()
}


fun solveLevel2(ranges: List<LongRange>): Long {

    // Filter out redundant ranges
    val filteredRanges =
        ranges.filterNot { range ->
            val isDuplicate = ranges.any {
                (it.first <= range.first && it.last >= range.last && range != it)/*.also { duplicate ->
                    if (duplicate) println(
                        "$range contained in $it"
                    )
                }*/
            }
            isDuplicate
        }
//    println("Filtered redundant ${ranges.size - filteredRanges.size}")

    val deduped = filteredRanges.withIndex()
        .filterNot { (index, range) ->
            filteredRanges.withIndex().any { it.value == range && it.index > index }
//                .also { if (it) println("deduped $range") }
        }.map { (_, range) -> range }

//    println("Deduped out ${filteredRanges.size - deduped.size}")

    val sorted = deduped.sortedBy { it.first }

    var freshCount = 0L
    for (i in sorted.indices) {
        val range = sorted[i]
        val start = range.first
        val nextStart = sorted.drop(i + 1).minOfOrNull { it.first } ?: Long.MAX_VALUE
        val end = min(range.last, nextStart - 1)
        if (start <= end) {
            freshCount += end - start + 1
        }
    }

    return freshCount
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
    runLevels(DAY, { solveLevel1(ranges, ids) }, { solveLevel2(ranges) })
}