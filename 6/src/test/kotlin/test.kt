package ua.olehlypskyi.adventofcode.sixth

import org.junit.Test
import kotlin.test.assertEquals

class SixthTests {
    @Test
    fun testFindStartMessageMarkerProcessedSymbolsCount() {
        val testInputs = listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
        )
        testInputs.forEach { (input, expected) ->
            val symbolsCount = findDistinctSymbolsSequence(input, START_MESSAGE_MARKER_SIZE)
            assertEquals(expected, symbolsCount, "Symbols count should be $expected in input: $input")
        }
    }

    @Test
    fun testFindStartPacketMarkerProcessedSymbolsCount() {
        val testInputs = listOf(
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
        )
        testInputs.forEach { (input, expected) ->
            val symbolsCount = findDistinctSymbolsSequence(input, START_PACKET_MARKER_SIZE)
            assertEquals(expected, symbolsCount, "Symbols count should be $expected in input: $input")
        }
    }
}

