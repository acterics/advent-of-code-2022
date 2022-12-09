package ua.olehlypskyi.adventofcode.ninth

import java.io.File
import kotlin.math.abs
import kotlin.math.sign

typealias Command = Pair<Direction, Int>

fun main() {
    val input = File("./input.txt").readText()
    val simulation = RopeSimulation(10)
    val commands = parseInput(input)

    commands.forEach { command ->
        performCommand(command, simulation)
    }

    val visitedStepsCount = simulation.visitedTailPositions.size
    println(visitedStepsCount)
}

fun performCommand(command: Command, simulation: RopeSimulation) {
    val (direction, count) = command
    (0 until count).forEach { _ ->
        simulation.performStep(direction)
    }
}

class RopeSimulation(ropeLength: Int) {
    val rope: Rope = Rope(ropeLength)

    val visitedTailPositions = mutableSetOf(
        Vector2.zero.let { it.x to it.y }
    )

    fun performStep(direction: Direction) {
        moveHead(direction)
        adjustRope()
    }

    fun moveHead(direction: Direction) {
        rope.head.position = when (direction) {
            Direction.UP -> Vector2(rope.head.position.x, rope.head.position.y - 1)
            Direction.DOWN -> Vector2(rope.head.position.x, rope.head.position.y + 1)
            Direction.LEFT -> Vector2(rope.head.position.x - 1, rope.head.position.y)
            Direction.RIGHT -> Vector2(rope.head.position.x + 1, rope.head.position.y)
        }
    }

    fun adjustRope() {
        var current: Rope.Knot? = rope.head
        while (current != null) {
            adjustKnot(current.next)
            current = current.next
        }

    }

    fun adjustKnot(current: Rope.Knot?) {
        current ?: return
        val prev = current.prev ?: return
        val distanceX = (prev.position.x - current.position.x)
        val distanceY = (prev.position.y - current.position.y)
        val (dx, dy) = when {
            abs(distanceX) > 1 && abs(distanceY) > 0 -> (distanceX / 2) to (distanceY + 1 * distanceY.sign) / 2
            abs(distanceX) > 0 && abs(distanceY) > 1 -> (distanceX + 1 * distanceX.sign) / 2 to distanceY / 2
            else -> distanceX / 2 to distanceY / 2
        }

        current.position = Vector2(
            x = current.position.x + dx,
            y = current.position.y + dy
        )
        if (current.next == null) {
            visitedTailPositions.add(current.position.x to current.position.y)
        }
    }

}

class Rope(size: Int) {
    class Knot(var position: Vector2, var next: Knot?, var prev: Knot?)

    val head: Knot

    init {
        head = Knot(Vector2.zero, next = null, prev = null)
        var current = head
        (0 until size - 1).forEach { _ ->
            val new = Knot(Vector2.zero, next = null, prev = current)
            current.next = new
            current = new
        }
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

@JvmInline
value class Vector2 private constructor(private val values: IntArray) {
    constructor(x: Int, y: Int) : this(intArrayOf(x, y))

    init {
        require(values.size >= 2)
    }

    val x: Int get() = values[0]
    val y: Int get() = values[1]

    companion object {
        val zero: Vector2 get() = Vector2(0, 0)
    }

}

fun parseInput(input: String): List<Command> = input.split("\n")
    .map { line ->
        val (directionInput, countString) = line.split(" ")
        parseDirection(directionInput) to countString.toInt()
    }

fun parseDirection(input: String): Direction = when (input) {
    "U" -> Direction.UP
    "L" -> Direction.LEFT
    "R" -> Direction.RIGHT
    "D" -> Direction.DOWN
    else -> throw IllegalArgumentException("Illegal direction $input")
}