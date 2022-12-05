package ua.olehlypskyi.adventofcode.fifth

import java.io.File
import java.lang.IllegalArgumentException

typealias Crate = Char
typealias CrateStack = MutableList<Crate>

data class CraneInstruction(
    val count: Int,
    val fromStack: Int,
    val toStack: Int
)

data class Input(
    val crateStacks: MutableList<CrateStack>,
    val craneInstructions: List<CraneInstruction>
)

fun main() {
    val input = File("./input.txt").readText()
    val topCrates = getTopCratesAfterRearrangement(input, ::performCrateMover9001Instruction)
    println(topCrates)
}


fun getTopCratesAfterRearrangement(
    input: String,
    performRearrangementInstruction: (MutableList<CrateStack>, CraneInstruction) -> Unit
) = parseInput(input)
    .let { parsedInput ->
        rearrangeCrates(
            parsedInput.crateStacks,
            parsedInput.craneInstructions,
            performRearrangementInstruction
        )
    }
    .getTopCrates()


fun rearrangeCrates(
    stacks: MutableList<CrateStack>,
    instructions: List<CraneInstruction>,
    performRearrangementInstruction: (MutableList<CrateStack>, CraneInstruction) -> Unit
): List<CrateStack> =
    instructions.fold(stacks) { stacksIteration, instruction ->
        performRearrangementInstruction(stacksIteration, instruction)
        stacksIteration
    }

fun parseInput(input: String): Input = input.split("\n\n").let { (cratesInput, instructionsInput) ->
    Input(
        crateStacks = parseCrateStacks(cratesInput).toMutableList(),
        craneInstructions = parseCraneInstructions(instructionsInput)
    )
}

fun parseCrateStacks(input: String): List<CrateStack> {
    val rows = input.split("\n")
    val crateColumnsIndices = rows.last()
        .mapIndexedNotNull { index, char ->
            if (char.isWhitespace()) {
                return@mapIndexedNotNull null
            }
            index
        }
    val stacks = crateColumnsIndices.map { mutableListOf<Char>() }
    rows.dropLast(1).reversed().forEach { row ->
        crateColumnsIndices.forEachIndexed { stackIndex, columnIndex ->
            val crate = row.getOrNull(columnIndex)?.takeIf { it.isLetter() } ?: return@forEachIndexed
            stacks[stackIndex].add(crate)
        }
    }
    return stacks
}

fun parseCraneInstructions(input: String): List<CraneInstruction> = input.split("\n").map { line ->
    parseCraneInstruction(line)
}

fun parseCraneInstruction(input: String): CraneInstruction {
    val regex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")
    val result = regex.find(input) ?: throw IllegalArgumentException("Cannot parse instruction $input")
    // First group for regex result contains full string
    val (count, from, to) = result.groupValues.drop(1).map { it.toInt() }
    return CraneInstruction(count, from, to)
}

fun List<CrateStack>.getTopCrates(): String = map { it.lastOrNull() ?: '_' }.joinToString("")

fun performCrateMover9000Instruction(stacks: MutableList<CrateStack>, instruction: CraneInstruction) {
    val originStack = stacks.getOrNull(instruction.fromStack - 1)
        ?: throw IllegalArgumentException("Cannot perform instruction $instruction for stacks $stacks. Origin not found")
    val targetStack = stacks.getOrNull(instruction.toStack - 1)
        ?: throw IllegalArgumentException("Cannot perform instruction $instruction for stacks $stacks. Target not found")
    (0 until instruction.count).forEach { _ ->
        val top = originStack.removeLast()
        targetStack.add(top)
    }
}

fun performCrateMover9001Instruction(stacks: MutableList<CrateStack>, instruction: CraneInstruction) {
    val originStack = stacks.getOrNull(instruction.fromStack - 1)
        ?: throw IllegalArgumentException("Cannot perform instruction $instruction for stacks $stacks. Origin not found")
    val targetStack = stacks.getOrNull(instruction.toStack - 1)
        ?: throw IllegalArgumentException("Cannot perform instruction $instruction for stacks $stacks. Target not found")
    val movingCrates = (0 until instruction.count).map { originStack.removeLast() }.reversed()
    targetStack.addAll(movingCrates)
}

