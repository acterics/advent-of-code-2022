package ua.olehlypskyi.adventofcode.twelve

import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min
import kotlin.math.pow

const val MAX_CLIMB_HEIGHT = 1

fun main() {
    val input = File("./input.txt").readText()
    val heightMap = parseInput(input)
    val shortestPath = findShortestPathFromLowestPoints(heightMap)
    logMapPath(heightMap, shortestPath)
    println(shortestPath.size)
}

fun getMinStepsCount(heightMap: HeightMap): Int {
    val path = findPath(heightMap, heightMap.start) ?: throw IllegalArgumentException("Cannot find path")
    logMapPath(heightMap, path)
    return path.size
}

fun findShortestPathFromLowestPoints(heightMap: HeightMap): List<Movement> {
    var minDepth = Int.MAX_VALUE
    val visited = HashMap<Vector2, Int>()
    return heightMap.lowestPoints.mapNotNull { start ->
        val path = findPath(heightMap, start, maxDepthThreshold = minDepth, initialVisited = visited)
        if (path != null) {
            println("Found shortest path from $start. ${path.size}")
            minDepth = min(minDepth, path.size)
        }
        path
    }
        .minByOrNull { it.size } ?: throw IllegalArgumentException("Cannot find any path")
}

fun findPath(
    heightMap: HeightMap,
    start: Vector2,
    maxDepthThreshold: Int = Int.MAX_VALUE,
    initialVisited: HashMap<Vector2, Int> = HashMap()
): List<Movement>? {
    val visited = initialVisited
    val queue = mutableListOf(QueueNode(start))
    var possiblePath = listOf<Movement>()
    val currentPath = LinkedList<Movement>()
    var minPathDepth: Int? = null

    while (queue.isNotEmpty()) {
        val (current, direction, depth) = queue.removeFirst()

        val movement = direction?.let { Movement(current, it, index = depth) }

        currentPath.removeAll { it.index >= depth }
        visited[current] = visited[current]?.let { min(it, depth) } ?: depth

        if (movement != null) {
            currentPath.add(movement)
        }

        if (current == heightMap.destination) {
            println("Found path from $start. $depth")
            minPathDepth = min(depth, minPathDepth ?: Int.MIN_VALUE)
            possiblePath = currentPath.toList()
            continue
        }
        val possibleMoves = Direction.values().filter { nextDirection ->
            canMove(heightMap.grid, current, nextDirection)
        }
            .map { nextDirection ->
                Movement(
                    position = current + nextDirection,
                    direction = nextDirection,
                    index = depth + 1
                )
            }
            .filter { nextMovement ->
                val visitedDepth = visited[nextMovement.position] ?: Int.MAX_VALUE
                nextMovement.index <= maxDepthThreshold && visitedDepth > nextMovement.index
            }

        possibleMoves.forEach { next ->
            queue.add(0, QueueNode(next.position, next.direction, next.index))
        }

    }
    if (minPathDepth == null) {
        return null
    }
    return possiblePath
}

class HeightMap(
    val grid: Array<CharArray>,
    val start: Vector2,
    val destination: Vector2,
    val lowestPoints: List<Vector2>
)

data class QueueNode(
    val position: Vector2,
    val direction: Direction? = null,
    val depth: Int = 0
)

data class Movement(
    val position: Vector2,
    val direction: Direction,
    val index: Int
)

data class Vector2(val x: Int, val y: Int)

operator fun Vector2.plus(direction: Direction): Vector2 = Vector2(
    x = x + direction.dx,
    y = y + direction.dy
)

operator fun Vector2.minus(direction: Direction): Vector2 = Vector2(
    x = x - direction.dx,
    y = y - direction.dy
)

fun distance(a: Vector2, b: Vector2): Double = (a.x - b.x).toDouble().pow(2) + (a.y - b.y).toDouble().pow(2)

enum class Direction(val dx: Int, val dy: Int) {
    RIGHT(1, 0),
    TOP(0, -1),
    BOTTOM(0, 1),
    LEFT(-1, 0),
}


fun canMove(grid: Array<CharArray>, position: Vector2, direction: Direction): Boolean {
    val newHeight = grid
        .getOrNull(position.y + direction.dy)
        ?.getOrNull(position.x + direction.dx)
        ?: return false
    return (newHeight - grid[position]) <= MAX_CLIMB_HEIGHT
}

fun parseInput(input: String): HeightMap {
    var start: Vector2? = null
    var destination: Vector2? = null
    val lowestPoints = mutableListOf<Vector2>()
    val grid = input.split("\n").mapIndexed { row, line ->
        line.mapIndexed() { column, symbol ->
            val position = Vector2(column, row)
            when (symbol) {
                'S' -> {
                    lowestPoints.add(position)
                    start = position
                    'a'
                }

                'E' -> {
                    destination = position
                    'z'
                }

                'a' -> {
                    lowestPoints.add(position)
                    symbol
                }

                in ('b'..'z') -> symbol
                else -> throw IllegalArgumentException("Illegal height $symbol at $row:$column")
            }
        }.toCharArray()
    }.toTypedArray()
    return HeightMap(
        grid = grid,
        start = start ?: throw IllegalArgumentException("start not found"),
        destination = destination ?: throw IllegalArgumentException("destination not found"),
        lowestPoints = lowestPoints
    )
}

operator fun Array<CharArray>.get(position: Vector2): Char = this[position.y][position.x]

class Color(val h: Int, val s: Int, val l: Int) {
    override fun toString(): String = "hsl($h, $s%, $l%)"
}

fun logMapPath(heightMap: HeightMap, path: List<Movement>) {
    val pathMap = path.associate { (it.position - it.direction) to it.direction }
    val output = heightMap.grid.indices.joinToString("\n") { row ->
        heightMap.grid[row].indices.joinToString("") { column ->
            val position = Vector2(x = column, y = row)
            val direction = pathMap[position]
            val height = heightMap.grid[position]
            val warm = 270 - (height - 'a') * (270 / ('z' - 'a'))
            val color = Color(warm, 100, 50)

            val symbol = if (direction != null) {
                when (direction) {
                    Direction.TOP -> "&nbsp;↑&nbsp;"
                    Direction.LEFT -> "←"
                    Direction.BOTTOM -> "&nbsp;↓&nbsp;"
                    Direction.RIGHT -> "→"
                }
            } else {
                "&nbsp&nbsp&nbsp&nbsp"
            }
            val textColor = "#FFFFFF"

            "<span style=\"background-color:$color;color:$textColor;\">$symbol</span>"
        }.let { "$it<br>" }
    }
    File("./output.html").writeText(output)
}