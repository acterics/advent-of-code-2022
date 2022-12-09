package ua.olehlypskyi.adventofcode.eighth

import java.io.File
import java.lang.Integer.max

typealias TreeGrid = Array<IntArray>

fun main() {
    val input = File("./input.txt").readText()
    val grid = parseInput(input)
    val visibleTreeCount = getVisibleTreeCount(grid)
    val getHighestScenicScore = getHighestTreeScenicScore(grid)
    println(getHighestScenicScore)
}

fun getHighestTreeScenicScore(grid: TreeGrid): Int {
    var maxScore = 0
    val rows = grid.size
    val columns = grid[0].size
    (1 until rows - 1).forEach { row ->
        (1 until columns - 1).forEach { column ->
            val score = getTreeScenicScore(grid, row, column)
            maxScore = max(score, maxScore)
        }
    }
    return maxScore
}

fun getVisibleTreeCount(grid: TreeGrid): Int {
    val rows = grid.size
    val columns = grid[0].size
    var count = columns * 2 + (rows - 2) * 2
    (1 until rows - 1).forEach { row ->
        (1 until columns - 1).forEach { column ->
            val isTreeVisible = isTreeVisible(grid, row, column)
            if (isTreeVisible) {
                count += 1
            }
        }
    }
    return count
}

fun isTreeVisible(grid: TreeGrid, row: Int, column: Int): Boolean {
    val tree = grid[row][column]
    var isVisibleInDirection: Boolean
    listOf(
        Pointer::top,
        Pointer::bottom,
        Pointer::left,
        Pointer::right
    ).forEach { direction ->
        isVisibleInDirection = true
        grid.forEachInDirection(row, column, direction) { lookupTree ->
            if (lookupTree >= tree) {
                isVisibleInDirection = false
            }
        }
        if (isVisibleInDirection) {
            return true
        }
    }
    return false
}

fun getTreeScenicScore(grid: TreeGrid, row: Int, column: Int): Int {
    val tree = grid[row][column]
    return listOf(
        Pointer::top,
        Pointer::bottom,
        Pointer::left,
        Pointer::right
    ).map toDirectionScore@{ direction ->
        var directionScore = 0
        grid.forEachInDirection(row, column, direction) { lookupTree ->
            directionScore += 1
            if (tree <= lookupTree) {
                return@toDirectionScore directionScore
            }
        }
        directionScore
    }
        .fold(1) { acc, score -> acc * score }
}


class Pointer(var row: Int, var column: Int) {
    fun top() {
        row -= 1
    }

    fun bottom() {
        row += 1
    }

    fun left() {
        column -= 1
    }

    fun right() {
        column += 1
    }
}

operator fun TreeGrid.get(pointer: Pointer): Int? = getOrNull(pointer.row)?.getOrNull(pointer.column)

inline fun TreeGrid.forEachInDirection(
    row: Int,
    column: Int,
    setNext: (Pointer) -> Unit,
    block: (Int) -> Unit
) {
    val pointer = Pointer(row, column)
    setNext(pointer)
    var current = this[pointer]
    while (current != null) {
        block(current)
        setNext(pointer)
        current = this[pointer]
    }
}


fun parseInput(input: String): TreeGrid = input.split("\n")
    .map { line -> parseInputLine(line) }
    .toTypedArray()

fun parseInputLine(line: String): IntArray = line.map { it.digitToInt() }.toIntArray()