package ua.olehlypskyi.adventofcode.fifth

import org.junit.Test
import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class FifthTests {

    @Test
    fun testGetTopCratesWithCrateMover9000() {
        val inputFile = File("./test-input.txt")
        val expectedTopCrates = "CMZ"
        val topCrates = getTopCratesAfterRearrangement(inputFile.readText(), ::performCrateMover9000Instruction)
        assertEquals(expectedTopCrates, topCrates)
    }

    @Test
    fun testGetTopCratesWithCrateMover9001() {
        val inputFile = File("./test-input.txt")
        val expectedTopCrates = "MCD"
        val topCrates = getTopCratesAfterRearrangement(inputFile.readText(), ::performCrateMover9001Instruction)
        assertEquals(expectedTopCrates, topCrates)
    }

    @Test
    fun testPerformCrateMover9000Instruction() {
        val stacks = mutableListOf(
            mutableListOf('Z', 'N'),
            mutableListOf('M', 'C', 'D'),
            mutableListOf('P')
        )
        val instruction1 = CraneInstruction(1, 2, 1)
        val instruction2 = CraneInstruction(3, 1, 3)
        val step1ExpectedStacks = mutableListOf(
            mutableListOf('Z', 'N', 'D'),
            mutableListOf('M', 'C'),
            mutableListOf('P')
        )
        val step2ExpectedStacks = mutableListOf(
            mutableListOf(),
            mutableListOf('M', 'C'),
            mutableListOf('P', 'D', 'N', 'Z')
        )
        performCrateMover9000Instruction(stacks, instruction1)
        assertContentEquals(step1ExpectedStacks, stacks)
        performCrateMover9000Instruction(stacks, instruction2)
        assertContentEquals(step2ExpectedStacks, stacks)
    }

    @Test
    fun testPerformCrateMover9001Instruction() {
        val stacks = mutableListOf(
            mutableListOf('Z', 'N'),
            mutableListOf('M', 'C', 'D'),
            mutableListOf('P')
        )
        val instruction1 = CraneInstruction(1, 2, 1)
        val instruction2 = CraneInstruction(3, 1, 3)
        val step1ExpectedStacks = mutableListOf(
            mutableListOf('Z', 'N', 'D'),
            mutableListOf('M', 'C'),
            mutableListOf('P')
        )
        val step2ExpectedStacks = mutableListOf(
            mutableListOf(),
            mutableListOf('M', 'C'),
            mutableListOf('P', 'Z', 'N', 'D')
        )
        performCrateMover9001Instruction(stacks, instruction1)
        assertContentEquals(step1ExpectedStacks, stacks)
        performCrateMover9001Instruction(stacks, instruction2)
        assertContentEquals(step2ExpectedStacks, stacks)
    }

    @Test
    fun testParseInput() {
        val inputFile = File("./test-input.txt")
        val expectedInput = Input(
            crateStacks = mutableListOf(
                mutableListOf('Z', 'N'),
                mutableListOf('M', 'C', 'D'),
                mutableListOf('P')
            ),
            craneInstructions = listOf(
                CraneInstruction(1, 2, 1),
                CraneInstruction(3, 1, 3),
                CraneInstruction(2, 2, 1),
                CraneInstruction(1, 1, 2),
            )
        )
        val input = parseInput(inputFile.readText())
        assertEquals(expectedInput, input)
    }

    @Test
    fun testParseCrateStacks() {
        val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
        """.trimIndent()
        val expectedStacks = mutableListOf(
            mutableListOf('Z', 'N'),
            mutableListOf('M', 'C', 'D'),
            mutableListOf('P')
        )
        val stacks = parseCrateStacks(input)

        assertContentEquals(expectedStacks, stacks)
    }

    @Test
    fun testParseCraneInstruction() {
        val line = "move 1 from 2 to 1"
        val expectedInstruction = CraneInstruction(1, 2, 1)
        val instruction = parseCraneInstruction(line)
        assertEquals(expectedInstruction, instruction)
    }

}

