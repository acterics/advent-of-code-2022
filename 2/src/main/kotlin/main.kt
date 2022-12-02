package ua.olehlypskyi.adventofcode.second

import java.io.File
import kotlin.math.sign

const val SCORE_LOSE = 0
const val SCORE_DRAW = 3
const val SCORE_WIN = 6

fun main(args: Array<String>) {
    val inputFile = File("./input.txt")
    val totalScore = calculateTotalScore(inputFile.readText())
    println("totalScore: $totalScore")
}

enum class Shape {
    Rock,
    Paper,
    Scissors;
}

fun calculateTotalScore(input: String): Int = parseInput(input)
    .fold(0) { acc, pair -> acc + calculateTotalRoundScore(pair.first, pair.second) }

fun calculateTotalRoundScore(opponent: Shape, player: Shape): Int =
    calculateRoundResultScore(opponent, player) + getPlayerShapeScore(player)

fun calculateRoundResultScore(opponent: Shape, player: Shape): Int {
    val roundResult = compareShapes(player, opponent)
    return when {
        roundResult > 0 -> SCORE_WIN
        roundResult < 0 -> SCORE_LOSE
        else -> SCORE_DRAW
    }
}

fun compareShapes(player: Shape, opponent: Shape): Int =
    when {
        player == Shape.Rock && opponent == Shape.Scissors -> 1
        player == Shape.Scissors && opponent == Shape.Rock -> -1
        else -> (player.ordinal - opponent.ordinal).sign
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

fun parseLine(line: String): Pair<Shape, Shape> = line.split(" ")
    .let { parts ->
        require(parts.size == 2)
        decodeOpponentShape(parts[0]) to decodePlayerShape(parts[1])
    }


fun parseInput(input: String): List<Pair<Shape, Shape>> = input
    .split("\n")
    .map(::parseLine)