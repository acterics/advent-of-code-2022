package ua.olehlypskyi.adventofcode.second

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class SecondDayTests {
    @Test
    fun testTotalScore() {
        val testInputFile = File("./test-input.txt")
        val expectedTotalScore = 15
        val actualTotalScore = calculateTotalScore(testInputFile.readText())
        assertEquals(expectedTotalScore, actualTotalScore)
    }

    @Test
    fun testShapesCompare() {
        Shape.values().forEach { shape1 ->
            Shape.values().forEach { shape2 ->
                val compareResult = compareShapes(shape1, shape2)
                when(shape1) {
                    Shape.Rock -> when(shape2) {
                        Shape.Rock -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                    }
                    Shape.Paper -> when(shape2) {
                        Shape.Rock -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                    }
                    Shape.Scissors -> when(shape2) {
                        Shape.Rock -> assertEquals(-1, compareResult, "$shape1 vs $shape2")
                        Shape.Paper -> assertEquals(1, compareResult, "$shape1 vs $shape2")
                        Shape.Scissors -> assertEquals(0, compareResult, "$shape1 vs $shape2")
                    }
                }
            }
        }
    }

}


