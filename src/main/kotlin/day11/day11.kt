package day11

import getInput
import runLevels

val cache = mutableMapOf<Pair<String, String>, Long>()
fun countPathsFromTo(from: String, to: String, conns: Map<String, Set<String>>): Long {
    val key = Pair(from, to)
    if (cache.containsKey(key)) {
        return cache[key]!!
    }
    if (from == to) return 1
    if (!conns.containsKey(from)) return 0
    return conns[from]!!.sumOf { countPathsFromTo(it, to, conns) }.also { cache[key] = it }
}

fun solveLevel1(conns: Map<String, Set<String>>): Long {
    return countPathsFromTo("you", "out", conns)
}

fun solveLevel2(conns: Map<String, Set<String>>): Long {


    return countPathsFromTo("svr", "dac", conns) *
            countPathsFromTo("dac", "fft", conns) *
            countPathsFromTo("fft", "out", conns) +
            countPathsFromTo("svr", "fft", conns) *
            countPathsFromTo("fft", "dac", conns) *
            countPathsFromTo("dac", "out", conns)
}

const val DAY = 11
const val USE_SAMPLE = false

fun main() {
    val text = getInput(DAY, USE_SAMPLE)

    val conns =
        text
            .lines()
            .filter { it.isNotBlank() }
            .associate { line ->
                val (start, outs) = line.split(": ")
                start to outs.split(" ").toSet()
            }
    runLevels(DAY, { solveLevel1(conns) }, { solveLevel2(conns) }, 1)
}
