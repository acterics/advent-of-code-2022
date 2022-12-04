package ua.olehlypskyi.adventofcode.fourth

import java.io.File

typealias AssignedSections = ClosedRange<Int>
typealias ElvesSections = Pair<AssignedSections, AssignedSections>

fun main() {
    val input = File("./input.txt").readText()
    val fullContainsResult = countFullyContainingPairs(input)
    val overlapResult = countOverlappingPairs(input)
    println("$overlapResult")
}

fun countFullyContainingPairs(input: String): Int = parseInput(input)
    .filter { (first, second) ->
        first.isFullyContain(second) || second.isFullyContain(first)
    }
    .size

fun countOverlappingPairs(input: String): Int = parseInput(input)
    .filter { (first, second) ->
        first.isOverlap(second)
    }
    .size

fun parseInput(input: String): List<ElvesSections> = input.split("\n")
    .map { line -> parseInputLine(line) }


fun parseInputLine(line: String): ElvesSections = splitToPair(line)
    .map { string -> convertToSectionsRange(string) }
    .let { (first, second) -> first to second }

fun splitToPair(line: String): List<String> = line.split(",")

fun convertToSectionsRange(string: String): AssignedSections {
    val (start, end) = string.split("-").map { it.toInt() }
    return (start..end)
}

fun AssignedSections.isFullyContain(sections: AssignedSections): Boolean =
    start <= sections.start && endInclusive >= sections.endInclusive

fun AssignedSections.isOverlap(sections: AssignedSections): Boolean =
    start <= sections.endInclusive && endInclusive >= sections.start

