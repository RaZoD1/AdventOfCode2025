package day08

import Vec3
import getInput
import runLevels

fun solveLevel1(junctions: List<Vec3>): Long {
    val circuits = mutableSetOf<Set<Vec3>>()
    circuits.addAll(junctions.map { setOf(it) })
    val junctionPairs = mutableSetOf<Pair<Vec3, Vec3>>()
    for ((index, junction) in junctions.withIndex()) {
        junctionPairs.addAll(junctions.drop(index + 1).map { junction to it })
    }
    val sortedPairs = junctionPairs.sortedBy { it.first.euclidean(it.second) }

    var pairIndex = 0
    while (pairIndex < PAIRS_TO_CONNECT) {
        if (sortedPairs.size <= pairIndex) {
            throw IndexOutOfBoundsException()
        }
        val pair = sortedPairs[pairIndex]
        pairIndex++
        val circuit1 = circuits.first { it.contains(pair.first) }
        val circuit2 = circuits.first { it.contains(pair.second) }
        if (circuit1 == circuit2) {
            // already in the same circuit
//            println("Skipped $pair in $circuit1")
            continue
        }
        circuits.remove(circuit1)
        circuits.remove(circuit2)
        circuits.add(circuit1 union circuit2)
//        println("Merged ${circuit1.size} and ${circuit2.size} Pair$pair")
    }

    return circuits
        .sortedByDescending { it.size }
        .take(3)
        .fold(1L) { acc, circ -> acc * circ.size }
}

fun solveLevel2(junctions: List<Vec3>): Long {
    val circuits = mutableSetOf<Set<Vec3>>()
    circuits.addAll(junctions.map { setOf(it) })
    val junctionPairs = mutableSetOf<Pair<Vec3, Vec3>>()
    for ((index, junction) in junctions.withIndex()) {
        junctionPairs.addAll(junctions.drop(index + 1).map { junction to it })
    }
    val sortedPairs = junctionPairs.sortedBy { it.first.euclidean(it.second) }

    for (pair in sortedPairs) {
        val circuit1 = circuits.first { it.contains(pair.first) }
        val circuit2 = circuits.first { it.contains(pair.second) }
        if (circuit1 == circuit2) {
            // already in the same circuit
//            println("Skipped $pair in $circuit1")
            continue
        }
        circuits.remove(circuit1)
        circuits.remove(circuit2)
        circuits.add(circuit1 union circuit2)
//        println("Merged ${circuit1.size} and ${circuit2.size} Pair$pair")
        if (circuits.size == 1) return pair.first.x * pair.second.x
    }
    error("Junction Boxes couldn't be connected to 1 circuit. This should not be possible")
}

const val DAY = 8
const val USE_SAMPLE = false
val PAIRS_TO_CONNECT = if (USE_SAMPLE) 10 else 1000

fun main() {
    val text = getInput(DAY, USE_SAMPLE)
    val junctions =
        text
            .lines()
            .filter(String::isNotBlank)
            .map { line ->
                val (x, y, z) = line.split(",").map(String::toLong)
                Vec3(x, y, z)
            }
    runLevels(DAY, { solveLevel1(junctions) }, { solveLevel2(junctions) })
}
