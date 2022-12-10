package ua.olehlypskyi.adventofcode.tenth

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class TenthTests {
    @Test
    fun testSumSignalStrengthRecords() {
        val input = File("./test-input.txt").readText()
        val simulation = Simulation()
        val program = parseInput(input)
        simulation.run(program)
        val expectedSum = 13140
        val sum = simulation.sumSignalStrengthRecords(6)
        assertEquals(expectedSum, sum)
    }

}

