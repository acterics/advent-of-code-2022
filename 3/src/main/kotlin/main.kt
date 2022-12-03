package ua.olehlypskyi.adventofcode.third

import java.io.File

typealias RucksackRawItems = String
typealias CompartmentItems = String
typealias RucksackItems = Pair<CompartmentItems, CompartmentItems>
typealias ItemType = Char
typealias ElvesGroup = List<RucksackRawItems>

const val ELVES_GROUP_SIZE = 3

fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    val badgesSum = calculateElvesGroupsBadgePrioritySum(inputFile.readText())
    println("$badgesSum")
}

fun calculateCommonItemsPrioritySum(input: String): Int = parseInput(input)
    .map { rawItems -> splitToCompartments(rawItems) }
    .mapNotNull { items -> findCommonItemType(items) }
    .sumOf { commonItem -> getItemTypePriority(commonItem) }

fun calculateElvesGroupsBadgePrioritySum(input: String): Int = splitToElvesGroups(parseInput(input))
    .mapNotNull { group -> findElvesGroupBadge(group) }
    .sumOf { badge -> getItemTypePriority(badge) }

fun parseInput(input: String): List<RucksackRawItems> = input.split("\n")

fun splitToElvesGroups(items: List<RucksackRawItems>): List<ElvesGroup> = items.chunked(ELVES_GROUP_SIZE)

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

fun findElvesGroupBadge(group: ElvesGroup): ItemType? {
    require(group.size == ELVES_GROUP_SIZE) {
        "Group size should be $ELVES_GROUP_SIZE"
    }
    return group[0].firstOrNull { itemType ->
        group[1].contains(itemType) && group[2].contains(itemType)
    }
}


fun getItemTypePriority(itemType: ItemType): Int = when {
    itemType.isUpperCase() -> itemType - 'A' + 27
    itemType.isLowerCase() -> itemType - 'a' + 1
    else -> -1
}
