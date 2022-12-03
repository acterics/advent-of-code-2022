package ua.olehlypskyi.adventofcode.third

import java.io.File

typealias RucksackRawItems = String
typealias CompartmentItems = String
typealias RucksackItems = Pair<CompartmentItems, CompartmentItems>
typealias ItemType = Char

fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    val prioritySum = calculateCommonItemsPrioritySum(inputFile.readText())
    println("$prioritySum")
}

fun calculateCommonItemsPrioritySum(input: String): Int = parseInput(input)
    .map { rawItems -> splitToCompartments(rawItems) }
    .mapNotNull { items -> findCommonItemType(items) }
    .sumOf { commonItem -> getItemTypePriority(commonItem) }

fun parseInput(input: String): List<RucksackRawItems> = input.split("\n")

fun splitToCompartments(rucksackRawItems: RucksackRawItems): RucksackItems {
    require(rucksackRawItems.length % 2 == 0) {
        "Rucksack items count should be even"
    }
    val compartmentSize = rucksackRawItems.length / 2
    return rucksackRawItems.take(compartmentSize) to rucksackRawItems.takeLast(compartmentSize)
}

fun findCommonItemType(items: RucksackItems): ItemType? = items.first.firstOrNull { firstCompartmentItem ->
    items.second.contains(firstCompartmentItem)
}

fun getItemTypePriority(itemType: ItemType): Int = when {
    itemType.isUpperCase() -> itemType - 'A' + 27
    itemType.isLowerCase() -> itemType - 'a' + 1
    else -> -1
}
