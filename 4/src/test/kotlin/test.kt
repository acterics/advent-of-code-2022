package ua.olehlypskyi.adventofcode.fourth

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FourthTests {

    @Test
    fun testCountOverlapsPairs() {
        val inputFile = File("./test-input.txt")
        val expectedResult = 4
        val actualResult = countOverlappingPairs(inputFile.readText())
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testCountFullyContainsPairs() {
        val inputFile = File("./test-input.txt")
        val expectedResult = 2
        val actualResult = countFullyContainingPairs(inputFile.readText())
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testConvertToSectionsRange() {
        val line = "1111-2222"
        val expectedRange = 1111..2222
        val actualRange = convertToSectionsRange(line)
        assertEquals(expectedRange, actualRange)
    }

    @Test
    fun testIsFullyContainsSections() {
        val fullyContainsSectionsPairs = listOf(
            (0..10) to (0..10),
            (0..10) to (1..9),
            (1..2) to (1..1),
            (1..1) to (1..1)
        )
        fullyContainsSectionsPairs.forEach { (first, second) ->
            assertTrue(first.isFullyContain(second), "Range $first should fully contains $second")
        }

        val notFullContainsSectionPairs = listOf(
            (1..10) to (0..9),
            (1..1) to (0..0),
            (1..1) to (2..2)
        )
        notFullContainsSectionPairs.forEach { (first, second) ->
            assertFalse(first.isFullyContain(second), "Range $first should not fully contains $second")
        }

    }

    @Test
    fun testIsOverlapSections() {
        val overlapSectionsPairs = listOf(
            (0..10) to (0..10),
            (0..10) to (1..9),
            (1..2) to (1..1),
            (1..1) to (1..1),
            (1..10) to (0..9),
            (5..6) to (1..6)
        )
        overlapSectionsPairs.forEach { (first, second) ->
            assertTrue(first.isOverlap(second), "Range $first should overlap $second")
        }

        val notOverlapSectionPairs = listOf(
            (1..5) to (6..7),
            (1..1) to (0..0),
            (1..1) to (2..2),
            (6..7) to (1..2)
        )
        notOverlapSectionPairs.forEach { (first, second) ->
            assertFalse(first.isOverlap(second), "Range $first should not overlap $second")
        }

    }

}

