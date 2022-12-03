package ua.olehlypskyi.adventofcode.third

import org.junit.Test
import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ThirdDayTests {

    @Test
    fun testCalculateElvesGroupsBadgePrioritySum() {
        val inputFile = File("./test-input.txt")
        val expectedSum = 70
        val actualSum = calculateElvesGroupsBadgePrioritySum(inputFile.readText())
        assertEquals(expectedSum, actualSum)
    }

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
    fun testFindElvesGroupBadge() {
        val group = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
        )
        val expectedBadge = 'r'
        val actualBadge = findElvesGroupBadge(group)
        assertEquals(expectedBadge, actualBadge)
    }

    @Test
    fun testSplitToCompartments() {
        val rawItems = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val expectedCompartmentItems = "vJrwpWtwJgWr" to "hcsFMMfFFhFp"
        val actualCompartmentItems = splitToCompartments(rawItems)

        assertEquals(expectedCompartmentItems, actualCompartmentItems)
    }

    @Test
    fun testSplitToElvesGroups() {
        val inputFile = File("./test-input.txt")
        val items = parseInput(inputFile.readText())
        val expectedGroups = listOf(
            listOf(
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
            ),
            listOf(
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw",
            )
        )
        val actualGroups = splitToElvesGroups(items)
        assertContentEquals(
            expectedGroups,
            actualGroups
        )
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