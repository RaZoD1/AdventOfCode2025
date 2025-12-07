package day02

import getInput
import runLevels


data class Range(val min: Long, val max: Long) {
    fun getInvalidIds(): List<Long> {
        return (min..max).filter {
            val str = it.toString()
            if (str.length % 2 != 0) false
            else
                if (str.take(str.length / 2) == str.takeLast(str.length / 2)) true
                else false
        }
    }

    fun getInvalidIds2(): List<Long> {
        return (min..max).filter {
            val str = it.toString()
            val len = str.length

            (1..(len / 2)).any { patternLen ->
                if (len % patternLen != 0) return@any false
                val times = len / patternLen;
                val pattern = str.take(patternLen)

                pattern.repeat(times) == str
            }
        }
    }
}

fun solveLevel1(turns: List<Range>): Long {
    return turns.sumOf { range -> range.getInvalidIds().sum() }
}

fun solveLevel2(turns: List<Range>): Long {
    return turns.sumOf { range -> range.getInvalidIds2().sum() }
}

const val DAY = 2

fun main() {
    val text = getInput(DAY)
    val ranges = text.lines().first().split(",")
        .map { range ->
            range.split('-')
                .let { Range(it.first().toLong(), it.last().toLong()) }
        }
    runLevels(DAY, { solveLevel1(ranges) }, { solveLevel2(ranges) })
}