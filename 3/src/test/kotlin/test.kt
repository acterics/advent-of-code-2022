package ua.olehlypskyi.adventofcode.thrid

import org.junit.Test
import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ThirdDayTests {

    @Test
    fun testCalculateCommonItemsPrioritySum() {
        val inputFile = File("./test-input.txt")
        val expectedSum = 157
        val actualSum = calculateCommonItemsPrioritySum(inputFile.readText())
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun testItemTypePriority() {
        assertEquals(1, getItemTypePriority('a'))
        assertEquals(2, getItemTypePriority('b'))
        assertEquals(27, getItemTypePriority('A'))
        assertEquals(28, getItemTypePriority('B'))
    }

    @Test
    fun testFindCommonItemType() {
        val items = "vJrwpWtwJgWr" to "hcsFMMfFFhFp"
        val expectedItemType = 'p'
        val actualItemType = findCommonItemType(items)
        assertEquals(expectedItemType, actualItemType)
    }

    @Test
    fun testSplitToCompartments() {
        val rawItems = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val expectedCompartmentItems = "vJrwpWtwJgWr" to "hcsFMMfFFhFp"
        val actualCompartmentItems = splitToCompartments(rawItems)

        assertEquals(expectedCompartmentItems, actualCompartmentItems)
    }

    @Test
    fun testParseInput() {
        val inputFile = File("./test-input.txt")
        val expectedParsedInput = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw",
        )

        val actualParsedInput = parseInput(inputFile.readText())
        assertContentEquals(
            expectedParsedInput,
            actualParsedInput
        )
    }

}