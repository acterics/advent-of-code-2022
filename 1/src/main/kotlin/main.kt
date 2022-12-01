package ua.olehlypskyi.adventofcode.first

import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    if (!inputFile.isFile) {
        throw IllegalStateException("input.txt not found")
    }
    val elvesCalories: MutableList<Int> = mutableListOf()
    var elfCalories = 0
    inputFile.forEachLine { line ->
        if (line.isBlank()) {
            elvesCalories.add(elfCalories)
            elfCalories = 0
            return@forEachLine
        }
        val caloriesItem = line.toIntOrNull() ?: return@forEachLine
        elfCalories += caloriesItem
    }
    elvesCalories.add(elfCalories)

    val topElvesSum = elvesCalories
        .sortedDescending()
        .asSequence()
        .take(3)
        .sum()

    println(topElvesSum)

}
