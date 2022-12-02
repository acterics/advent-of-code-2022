package ua.olehlypskyi.adventofcode.second

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class SecondDayTests {
    @Test
    fun testFirstStratTotalScore() {
        val testInputFile = File("./test-input.txt")
        val expectedTotalScore = 15
        val actualTotalScore = calculateFirstStratTotalScore(testInputFile.readText())
        assertEquals(expectedTotalScore, actualTotalScore)
    }

    @Test
    fun testSecondStratTotalScore() {
        val testInputFile = File("./test-input.txt")
        val expectedTotalScore = 12
        val actualTotalScore = calculateSecondStratTotalScore(testInputFile.readText())
        assertEquals(expectedTotalScore, actualTotalScore)
    }

    @Test
    fun testShapesCompare() {
        Shape.values().forEach { shape1 ->
            Shape.values().forEach { shape2 ->
                val compareResult = compareShapes(shape1, shape2)
                when (shape1) {
                    Shape.Rock -> when (shape2) {
                        Shape.Rock -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                    }

                    Shape.Paper -> when (shape2) {
                        Shape.Rock -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                    }

                    Shape.Scissors -> when (shape2) {
                        Shape.Rock -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                    }
                }
            }
        }
    }

    @Test
    fun testPlayerShapeFromResult() {
        Shape.values().forEach { opponent ->
            listOf(-1, 0, 1).forEach { result ->
                val playerShape = getPlayerShape(opponent, result)
                when (opponent) {
                    Shape.Rock -> when {
                        result > 0 -> assertEquals(Shape.Paper, playerShape, "? vs $opponent = $result")
                        result < 0 -> assertEquals(Shape.Scissors, playerShape, "? vs $opponent = $result")
                        else -> assertEquals(Shape.Rock, playerShape, "? vs $opponent = $result")
                    }
                    Shape.Paper -> when {
                        result > 0 -> assertEquals(Shape.Scissors, playerShape, "? vs $opponent = $result")
                        result < 0 -> assertEquals(Shape.Rock, playerShape, "? vs $opponent = $result")
                        else -> assertEquals(Shape.Paper, playerShape, "? vs $opponent = $result")
                    }

                    Shape.Scissors -> when {
                        result > 0 -> assertEquals(Shape.Rock, playerShape, "? vs $opponent = $result")
                        result < 0 -> assertEquals(Shape.Paper, playerShape, "? vs $opponent = $result")
                        else -> assertEquals(Shape.Scissors, playerShape, "? vs $opponent = $result")
                    }
                }
            }
        }
    }

}


