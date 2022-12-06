package ua.olehlypskyi.adventofcode.sixth

import java.io.File

const val START_PACKET_MARKER_SIZE = 4
const val START_MESSAGE_MARKER_SIZE = 14

fun main() {
    val input = File("./input.txt").readText()
    val startPackerSymbolsCount = findDistinctSymbolsSequence(input, START_PACKET_MARKER_SIZE)
    val startMessageSymbolsCount = findDistinctSymbolsSequence(input, START_MESSAGE_MARKER_SIZE)
    println("$startMessageSymbolsCount")
}

fun findDistinctSymbolsSequence(input: String, size: Int): Int {
    val markerBuffer = mutableListOf<Char>()
    input.forEachIndexed { index, char ->
        val repeatIndex = markerBuffer.indexOf(char)
        if (repeatIndex != -1) {
            (0..repeatIndex).forEach { _ ->
                markerBuffer.removeFirst()
            }
        }
        markerBuffer.add(char)
        if (markerBuffer.size == size) {
            return index + 1
        }
    }
    throw IllegalArgumentException("Distinct symbols sequence with size $size not found in $input")
}


