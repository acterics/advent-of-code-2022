package ua.olehlypskyi.adventofcode.eighth

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class EighthTests {

    @Test
    fun testGetHighestScenicScore() {
        val input = File("./test-input.txt").readText()
        val grid = parseInput(input)
        val expectedScore = 8
        val score = getHighestTreeScenicScore(grid)
        assertEquals(expectedScore, score)
    }

    @Test
    fun testGetTreeScenicScore() {
        val input = File("./test-input.txt").readText()
        val grid = parseInput(input)
        val cells = listOf(
            listOf(1, 2, 4),
            listOf(3, 2, 8)
        )
        cells.forEach { (row, column, expectedScore) ->
            val score = getTreeScenicScore(grid, row, column)
            assertEquals(expectedScore, score, "Score at ($row, $column) should be $expectedScore")
        }
    }

    @Test
    fun testGetVisibleTreeCount() {
        val input = File("./test-input.txt").readText()
        val grid = parseInput(input)
        val expectedCount = 21
        val count = getVisibleTreeCount(grid)
        assertEquals(expectedCount, count)
    }

}

