package ua.olehlypskyi.adventofcode.ninth

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class NinthTests {

    @Test
    fun testVisitedTailPositionsForSize10() {
        val input = File("./test-input.txt").readText()
        val simulation = RopeSimulation(10)
        val commands = parseInput(input)

        commands.forEach { command ->
            performCommand(command, simulation)
        }

        val expectedStepsCount = 1
        val visitedStepsCount = simulation.visitedTailPositions.size

        assertEquals(expectedStepsCount, visitedStepsCount)
    }

    @Test
    fun testVisitedTailPositionsForSize2() {
        val input = File("./test-input.txt").readText()
        val simulation = RopeSimulation(2)
        val commands = parseInput(input)

        commands.forEach { command ->
            performCommand(command, simulation)
        }

        val expectedStepsCount = 13
        val visitedStepsCount = simulation.visitedTailPositions.size

        assertEquals(expectedStepsCount, visitedStepsCount)
    }

}

