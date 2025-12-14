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

fun solveLevel1(problems: List<List<Long>>, operators: List<Operator>): Long {
    return problems
        .zip(operators)
        .sumOf { (problem, op) ->
            problem.reduce { a, b -> op.combine(a, b) }
        }
}


fun solveLevel2(problems: List<List<Long>>, operators: List<Operator>): Long {
    return -1;
}

const val DAY = 6

val whitespaceRegex = Regex("""\s+""")

fun main() {
    val text = getInput(DAY)
    val lines = text.lines().filter(String::isNotBlank)
    val numbers = lines.dropLast(1)
        .map { line -> line.split(whitespaceRegex).filter(String::isNotBlank).map(String::toLong) }
    val operators = lines.last().split(whitespaceRegex).filter(String::isNotBlank).map { Operator.from(it.first()) }

    val problems = transposeMatrix(numbers)

    runLevels(DAY, { solveLevel1(problems, operators) }, { solveLevel2(problems, operators) })
}