package ua.olehlypskyi.adventofcode.seventh

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class SeventhTests {

    @Test
    fun testSumAtMostDirs() {
        val input = File("./test-input.txt").readText()
        val console = Console()
        parseInput(input, console)
        val expectedSum = 95437
        val actualSum = console.sizeTable.filterValues { it <= 100_000 }
            .toList()
            .sumOf { it.second }

        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun testMinDirForUpdateSize() {
        val input = File("./test-input.txt").readText()
        val console = Console()
        parseInput(input, console)
        val expectedSize = 24933642
        val actualSize = getDirToDeleteSize(console)
        assertEquals(expectedSize, actualSize)
    }

}

