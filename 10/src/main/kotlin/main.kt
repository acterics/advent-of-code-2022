package ua.olehlypskyi.adventofcode.tenth

import java.io.File

fun main() {
    val input = File("./input.txt").readText()
    val simulation = Simulation()
    val program = parseInput(input)
    simulation.run(program)
    simulation.render()
}

sealed class Command(val cycles: Int) {
    object NoOp : Command(1)
    class AddX(val value: Int) : Command(2)
}

class Program(val commands: List<Command>)

class Simulation {
    private var cycle: Int = 1
    private var registerX: Int = 1

    private val signalStrengthRecords = mutableListOf<Int>()
    private val displayPixels = mutableListOf<Char>()

    fun run(program: Program) {
        cycle = 1
        var commandIndex = 0
        var commandCycle = 1

        while (commandIndex < program.commands.size) {
            trackSignalStrength()
            drawPixel()
            val isCommandExecuted = executeCommand(program.commands[commandIndex], commandCycle)
            if (isCommandExecuted) {
                commandCycle = 1
                commandIndex += 1
            } else {
                commandCycle += 1
            }
            cycle += 1
        }
    }

    fun drawPixel() {
        val position = (cycle - 1) % CRT_WIDTH
        val spriteRange = ((registerX - (SPRITE_SIZE - 1) / 2)..(registerX + (SPRITE_SIZE - 1) / 2))
        val pixel = if (position in spriteRange) {
            LIT_PIXEL
        } else {
            DARK_PIXEL
        }
        displayPixels.add(pixel)
    }

    fun render() {
        val display = (0 until CRT_HEIGHT).joinToString("\n") { row ->
            (0 until CRT_WIDTH)
                .map { column -> displayPixels.getOrNull(row * CRT_WIDTH + column) ?: ' ' }
                .joinToString("")
        }
        print("\u001Bc")
        println(display)

    }

    fun sumSignalStrengthRecords(count: Int): Int = signalStrengthRecords.take(count)
        .sum()

    private fun executeCommand(command: Command, commandCycle: Int): Boolean {
        if (commandCycle < command.cycles) {
            return false
        }
        when (command) {
            is Command.NoOp -> Unit
            is Command.AddX -> registerX += command.value
        }
        return true
    }

    private fun trackSignalStrength() {
        if ((cycle - 20) % 40 == 0) {
            signalStrengthRecords.add(registerX * cycle)
        }
    }

    companion object {
        const val SPRITE_SIZE = 3
        const val CRT_WIDTH = 40
        const val CRT_HEIGHT = 6
        const val LIT_PIXEL = '#'
        const val DARK_PIXEL = '.'
    }
}


fun parseInput(input: String) = input.split("\n")
    .map { line -> parseCommand(line) }
    .let { commands -> Program(commands) }

fun parseCommand(line: String): Command {
    val parts = line.split(" ")
    return when (parts[0]) {
        "noop" -> Command.NoOp
        "addx" -> Command.AddX(parts[1].toInt())
        else -> throw IllegalArgumentException("Illegal command $line")
    }
}