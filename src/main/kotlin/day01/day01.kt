package day01

import getInput
import runLevels


data class Turn(val dir: Char, val steps: Int) {
    val sign get() = if (dir == 'L') -1 else 1

    override fun toString(): String {
        return "$dir$steps"
    }
}

const val START = 50L;
const val SIZE = 100L;

fun solveLevel1(turns: List<Turn>): Long {

    val (endPos, zeroes) = turns.fold(Pair(START, 0L)) { (pos, zeroes), turn ->
        val newPos = (pos + turn.steps * turn.sign + SIZE) % SIZE
        Pair(newPos, if (newPos == 0L) zeroes + 1 else zeroes)
    }
    return zeroes;
}

fun solveLevel2(turns: List<Turn>): Long {
    val (endPos, zeroes) = turns.fold(Pair(START, 0L)) { (pos, zeroes), turn ->
        val steps = turn.steps % SIZE;
        val extraRounds = turn.steps / SIZE;
        val next = pos + steps * turn.sign
        val newPos = (next + SIZE) % SIZE
        val wrapAround = next  != newPos && pos != 0L;

        val newZeroes = zeroes + extraRounds + if (wrapAround || newPos == 0L) 1 else 0

//        if(turn.steps > 99) println("!!!!!")
//        println("Turn $turn: at $pos to $newPos -- ${wrapAround || newPos == 0L} $newZeroes")
        Pair(newPos, newZeroes)
    }
    return zeroes;
}

fun main() {
    val text = getInput(1)
    val turns = text.lines().filter { it.isNotBlank() }.map { Turn(it[0], it.drop(1).toInt()) }
    runLevels(1, { solveLevel1(turns) }, { solveLevel2(turns) })
}