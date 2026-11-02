package day03

import getInput
import runLevels

fun solveLevel1(banks: List<String>): Long {
    val totalJoltage =
        banks.sumOf { bank ->
            val digits = bank.map { it.digitToInt() }.withIndex().toList()
            val first = digits.dropLast(1).maxBy { it.value }
            val second = digits.subList(first.index + 1, bank.length).maxBy { it.value }

            val bankJoltage = first.value * 10 + second.value
//        println("$bank : First $first and Second $second --> $bankJoltage")
            bankJoltage.toLong()
        }
    return totalJoltage
}

fun solveLevel2(banks: List<String>): Long {
    val COUNT = 12

    val totalJoltage =
        banks.sumOf { bank ->
            val digits = bank.map { it.digitToInt() }.withIndex().toList()
            val activated = mutableListOf<IndexedValue<Int>>()

            var lastIndex = -1
            for (i in 1..COUNT) {
                val activatedDigit = digits.filter { it.index > lastIndex }.dropLast(COUNT - i).maxBy { it.value }
                activated += activatedDigit
                lastIndex = activatedDigit.index
            }

//        println("$bank : ${activated.joinToString("")}")

            val bankJoltage = activated.fold(0L) { acc, el -> acc * 10L + el.value }
            bankJoltage
        }
    return totalJoltage
}

const val DAY = 3

fun main() {
    val text = getInput(DAY)
    val banks = text.lines().filter { it.isNotBlank() }
    runLevels(DAY, { solveLevel1(banks) }, { solveLevel2(banks) })
}
