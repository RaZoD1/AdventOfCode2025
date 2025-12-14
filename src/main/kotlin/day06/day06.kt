package day06

import getInput
import runLevels
import transposeMatrix

enum class Operator(val symbol: Char, val combine: (Long, Long) -> Long) {
    PLUS('+', { a, b -> a + b }), MULTIPLY('*', { a, b -> a * b });

    companion object {
        fun from(char: Char) = when (char) {
            '+' -> PLUS
            '*' -> MULTIPLY
            else -> throw IllegalArgumentException("Invalid operator $char")
        }
    }


}

fun solveLevel1(lines: List<String>): Long {
    val numbers = lines.dropLast(1)
        .map { line -> line.split(whitespaceRegex).filter(String::isNotBlank).map(String::toLong) }
    val operators = lines.last().split(whitespaceRegex).filter(String::isNotBlank).map { Operator.from(it.first()) }

    val problems = transposeMatrix(numbers)

    return problems
        .zip(operators)
        .sumOf { (problem, op) ->
            problem.reduce { a, b -> op.combine(a, b) }
        }
}


fun groupStringsByEmptySeparator(inputList: List<String>): List<List<String>> {
    // Initial accumulator: an empty list of lists, and an empty current list
    val initialAccumulator = Pair(mutableListOf<MutableList<String>>(), mutableListOf<String>())

    val (groupedLists, lastGroup) = inputList.fold(initialAccumulator) { (accLists, accCurrentGroup), item ->
        if (item.isEmpty()) {
            if (accCurrentGroup.isNotEmpty()) {
                accLists.add(accCurrentGroup)
            }
            Pair(accLists, mutableListOf()) // Start a new current group
        } else {
            accCurrentGroup.add(item)
            Pair(accLists, accCurrentGroup) // Continue with the current group
        }
    }

    // Add the last group if it's not empty
    if (lastGroup.isNotEmpty()) {
        groupedLists.add(lastGroup)
    }

    // Return an immutable list of immutable lists
    return groupedLists.map { it.toList() }
}

fun solveLevel2(lines: List<String>): Long {
    val operators = lines
        .last()
        .split(whitespaceRegex)
        .filter(String::isNotBlank)
        .map { Operator.from(it.first()) }
        .reversed()
    // build a Matrix of chars and pad it for transposing
    val numMatrix = lines
        .dropLast(1)
        .map { it.toCharArray().toList() }
        .let { lines ->
            val maxLen = lines.maxOf { it.size }
            lines.map { it + List(maxLen - it.size) { ' ' } }
        }
    // transpose the prepared matrix
    val transposed = transposeMatrix(numMatrix).reversed()

    // collapse the empty spaces in the columns
    val cols = transposed.map { colChars -> colChars.joinToString("").replace(" ", "") }

    // group the columns into problems by looking at the empty columns
    val problems = groupStringsByEmptySeparator(cols).map { group -> group.map { it.toLong() } }

    return problems
        .zip(operators)
        .sumOf { (problem, op) ->
            problem.reduce { a, b -> op.combine(a, b) }
        }
}

const val DAY = 6

val whitespaceRegex = Regex("""\s+""")

fun main() {
    val text = getInput(DAY)
    val lines = text.lines().filter(String::isNotBlank)

    runLevels(DAY, { solveLevel1(lines) }, { solveLevel2(lines) })
}