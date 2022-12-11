package ua.olehlypskyi.adventofcode.eleventh

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class EleventhTests {

    @Test
    fun testComputeMonkeyBusiness1() {
        val testInput = File("./test-input.txt").readText()
        val monkeys = parseInput(testInput)
        val game = Game(monkeys, 20, defaultReduceItemWorryOperation)
        game.start()
        val expectedMonkeyBusiness = 10605L
        val monkeyBusiness = game.computeMonkeyBusiness()

        assertEquals(expectedMonkeyBusiness, monkeyBusiness)
    }

    @Test
    fun testComputeMonkeyBusiness2() {
        val testInput = File("./test-input.txt").readText()
        val monkeys = parseInput(testInput)
        val game = Game(monkeys, 10_000, null)
        game.start()
        val expectedMonkeyBusiness = 2_713_310_158
        val monkeyBusiness = game.computeMonkeyBusiness()

        assertEquals(expectedMonkeyBusiness, monkeyBusiness)
    }

}

