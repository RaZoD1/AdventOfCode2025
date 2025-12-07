package day01

import getInput
import runLevels

fun solveLevel1(text: String): Long {
    throw NotImplementedError()
}

fun solveLevel2(text: String): Long {
    throw NotImplementedError()
}

fun main() {
    val text = getInput(1)
    runLevels(1, { solveLevel1(text) }, { solveLevel2(text) })
}