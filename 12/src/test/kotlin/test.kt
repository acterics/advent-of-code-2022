package ua.olehlypskyi.adventofcode.twelve

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class TwelveTests {

    @Test
    fun testFindShortestPathFromLowestPoints() {
        val input = File("./test-input.txt").readText()
        val heightMap = parseInput(input)
        val expectedStepsCount = 29
        val path = findShortestPathFromLowestPoints(heightMap)
        logMapPath(heightMap, path)
        assertEquals(expectedStepsCount, path.size)
    }

    @Test
    fun testPathStepsCount() {
        val input = File("./test-input.txt").readText()
        val heightMap = parseInput(input)
        val expectedStepsCount = 31
        val minStepsCount = getMinStepsCount(heightMap)
        assertEquals(expectedStepsCount, minStepsCount)
    }

    @Test
    fun testParseInput() {
        val input = File("./test-input.txt").readText()
        val heightMap = parseInput(input)
        val expectedStart = Vector2(0, 0)
        val expectedDestination = Vector2(5, 2)
        val expectedWidth = 8
        val expectedHeight = 5
        assertEquals(expectedHeight, heightMap.grid.size, "Height should be $expectedHeight")
        assertEquals(expectedWidth, heightMap.grid[0].size, "Width should be $expectedWidth")
        assertEquals(expectedStart, heightMap.start, "Start should be $expectedStart")
        assertEquals(expectedDestination, heightMap.destination, "Destination should be $expectedStart")

    }

}

