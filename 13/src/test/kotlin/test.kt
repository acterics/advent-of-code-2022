package ua.olehlypskyi.adventofcode.thirteen

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class ThirteenTests {

    @Test
    fun testComputeDecoderKey() {
        val testInput = File("./test-input.txt").readText()
        val pairs = parseInput(testInput)
        val expectedKey = 140
        val key = computeDecoderKey(pairs)
        assertEquals(expectedKey, key)
    }

    @Test
    fun testSumRightOrderIndices() {
        val testInput = File("./test-input.txt").readText()
        val pairs = parseInput(testInput)
        val expectedSum = 13
        val sum = sumRightOrderIndices(pairs)
        assertEquals(expectedSum, sum)
    }

    @Test
    fun testParsePacketListData() {
        val input = "[1,[2,[3,[4,[5,6,7]]]],8,9]"
        val expectedData = PacketData.ListData(
            listOf(
                PacketData.ValueData(1),
                PacketData.ListData(
                    listOf(
                        PacketData.ValueData(2),
                        PacketData.ListData(
                            listOf(
                                PacketData.ValueData(3),
                                PacketData.ListData(
                                    listOf(
                                        PacketData.ValueData(4),
                                        PacketData.ListData(
                                            listOf(
                                                PacketData.ValueData(5),
                                                PacketData.ValueData(6),
                                                PacketData.ValueData(7),
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                ),
                PacketData.ValueData(8),
                PacketData.ValueData(9)
            )
        )

        val data = parsePacketListData(input)
        assertEquals(expectedData, data)
    }

}

