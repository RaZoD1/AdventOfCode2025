package day10

import getInput
import runLevels

const val LIGHT_ON = "#"
const val LIGHT_OFF = "."

fun calcBtnPressResult(
    pressMap: ULong,
    buttons: List<ULong>,
): ULong {
    var lights = 0uL
    for (i in 0 until buttons.size) {
        val doPress = pressMap and (1uL shl i) > 0uL
        if (doPress) {
            lights = lights xor buttons[i]
        }
    }
    return lights
}

fun solveLevel1(panels: List<Panel>): Long =
    panels.sumOf { panel ->
        val len = panel.lights.size
        val goalBitmap = panel.lights.toBitmap()

        val possibleSolutions = mutableSetOf<ULong>()
        for (pressMap in 0uL until (1uL shl panel.buttons.size)) {
            val resultingLights = calcBtnPressResult(pressMap, panel.buttons)
            if (resultingLights != goalBitmap) {
                continue
            }
            possibleSolutions.add(pressMap)
        }

        val minPresses = possibleSolutions.minOf { it.countOneBits() }
        minPresses.toLong()
    }

fun solveLevel2(panels: List<Panel>): Long = -1L

fun indicesToBitMap(indices: Iterable<Int>): ULong {
    var bits = 0uL

    for (i in indices) {
        bits = bits or (1uL shl i)
    }
    return bits
}

fun Iterable<Boolean>.toBitmap(): ULong {
    var bits = 0uL
    for (bool in this.reversed()) {
        bits = (bits shl 1) or if (bool) 1uL else 0uL
    }
    return bits
}

data class Panel(
    val lights: List<Boolean>,
    val buttons: List<ULong>,
    val joltages: List<Long>,
)

const val DAY = 10
const val USE_SAMPLE = false

fun main() {
    val text = getInput(DAY, USE_SAMPLE)

    val panels =
        text
            .lines()
            .filter { it.isNotBlank() }
            .map { line ->

                val sections = line.split(" ")
                val lights =
                    sections[0]
                        .removeSurrounding("[", "]")
                        .chunked(1)
                        .map {
                            when (it) {
                                LIGHT_ON -> true
                                LIGHT_OFF -> false
                                else -> error("Unknown light $it in $line")
                            }
                        }
                val buttons =
                    sections
                        .drop(1)
                        .dropLast(1)
                        .map { btn ->
                            val btnIndices = btn.removeSurrounding("(", ")").split(",").map(String::toInt)
                            val btnBitmap = indicesToBitMap(btnIndices)
                            return@map btnBitmap
                        }

                val joltages =
                    sections
                        .last()
                        .removeSurrounding("{", "}")
                        .split(",")
                        .map { it.toLong() }

                Panel(lights, buttons, joltages)
            }
    runLevels(DAY, { solveLevel1(panels) }, { solveLevel2(panels) }, 1)
}
