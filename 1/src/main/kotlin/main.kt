package ua.olehlypskyi.adventofcode.first

import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    if (!inputFile.isFile) {
        throw IllegalStateException("input.txt not found")
    }
    val elvesCalories: MutableList<List<Int>> = mutableListOf()
    var elfCalories: MutableList<Int> = mutableListOf()
    inputFile.forEachLine { line ->
        if (line.isBlank()) {
            if (elfCalories.isNotEmpty()) {
                elvesCalories.add(elfCalories)
                elfCalories = mutableListOf()
            }
            return@forEachLine
        }
        val caloriesItem = line.toIntOrNull() ?: return@forEachLine
        elfCalories.add(caloriesItem)
    }
    val maxCalories = elvesCalories.maxOfOrNull { it.sum() }
        ?: throw IllegalStateException("invalid input.txt")

    println(maxCalories)
}