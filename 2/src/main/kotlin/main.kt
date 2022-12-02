package ua.olehlypskyi.adventofcode.second

import java.io.File
import kotlin.math.sign

const val SCORE_LOSE = 0
const val SCORE_DRAW = 3
const val SCORE_WIN = 6

enum class Shape {
    Rock,
    Paper,
    Scissors;
}
fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    val totalScore = calculateSecondStratTotalScore(inputFile.readText())
    println("totalScore: $totalScore")
}

/**
 * First strategy
 */
fun calculateFirstStratTotalScore(input: String): Int = parseFirstStratInput(input)
    .fold(0) { acc, pair -> acc + calculateFirstStratTotalRoundScore(pair.first, pair.second) }

fun calculateFirstStratTotalRoundScore(opponent: Shape, player: Shape): Int =
    calculateRoundResultScore(opponent, player) + getPlayerShapeScore(player)

fun parseFirstStratLine(line: String): Pair<Shape, Shape> = line.split(" ")
    .let { parts ->
        require(parts.size == 2)
        decodeOpponentShape(parts[0]) to decodePlayerShape(parts[1])
    }

fun parseFirstStratInput(input: String): List<Pair<Shape, Shape>> = input
    .split("\n")
    .map(::parseFirstStratLine)

/**
 * Second strategy
 */

fun calculateSecondStratTotalScore(input: String): Int = parseSecondStratInput(input)
    .fold(0) { acc, pair -> acc + calculateSecondStratTotalRoundScore(pair.first, pair.second) }

fun calculateSecondStratTotalRoundScore(opponent: Shape, result: Int): Int {
    val playerShape: Shape = getPlayerShape(opponent, result)
    return getRoundResultScore(result) + getPlayerShapeScore(playerShape)
}

fun parseSecondStratLine(line: String): Pair<Shape, Int> = line.split(" ")
    .let { parts ->
        require(parts.size == 2)
        decodeOpponentShape(parts[0]) to decodeRoundResult(parts[1])
    }

fun parseSecondStratInput(input: String): List<Pair<Shape, Int>> = input
    .split("\n")
    .map(::parseSecondStratLine)



fun calculateRoundResultScore(opponent: Shape, player: Shape): Int = getRoundResultScore(
    compareShapes(
        player = player,
        opponent = opponent
    )
)

fun getRoundResultScore(result: Int): Int = when {
    result > 0 -> SCORE_WIN
    result < 0 -> SCORE_LOSE
    else -> SCORE_DRAW
}

fun compareShapes(player: Shape, opponent: Shape): Int =
    when {
        player == Shape.Rock && opponent == Shape.Scissors -> 1
        player == Shape.Scissors && opponent == Shape.Rock -> -1
        else -> (player.ordinal - opponent.ordinal).sign
    }

fun getPlayerShape(opponent: Shape, result: Int): Shape =
    when {
        opponent == Shape.Rock && result.sign == -1 -> Shape.Scissors
        opponent == Shape.Scissors && result.sign == 1 -> Shape.Rock
        else -> Shape.values()[opponent.ordinal + result.sign]
    }

fun getPlayerShapeScore(shape: Shape): Int = when (shape) {
    Shape.Rock -> 1
    Shape.Paper -> 2
    Shape.Scissors -> 3
}

fun decodeOpponentShape(string: String): Shape = when (string) {
    "A" -> Shape.Rock
    "B" -> Shape.Paper
    "C" -> Shape.Scissors
    else -> throw IllegalArgumentException("Illegal line for decoding opponent shape")
}

fun decodePlayerShape(string: String): Shape = when (string) {
    "X" -> Shape.Rock
    "Y" -> Shape.Paper
    "Z" -> Shape.Scissors
    else -> throw IllegalArgumentException("Illegal line for decoding player shape")
}

fun decodeRoundResult(string: String): Int = when (string) {
    "X" -> -1
    "Y" -> 0
    "Z" -> 1
    else -> throw IllegalArgumentException("Illegal line for decoding round result")
}

