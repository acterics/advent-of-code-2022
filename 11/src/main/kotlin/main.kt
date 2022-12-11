package ua.olehlypskyi.adventofcode.eleventh

import java.io.File

fun main() {
    val input = File("./input.txt").readText()
    val monkeys = parseInput(input)
    val game = Game(monkeys, 10_000, null)
    game.start()
    val monkeyBusiness = game.computeMonkeyBusiness()
    println(monkeyBusiness)

}

class Game(
    val monkeys: List<Monkey>,
    val rounds: Int,
    val reduceWorryOperation: ((Item) -> Unit)?
) {

    val commonDivider by lazy {
        monkeys.map { it.worryTest.divisibleNumber }
            .fold(1L, Long::times)
    }

    fun start() {
        (1..rounds).forEach { round ->
            monkeys.forEach { monkey ->
                var currentItem: Item? = monkey.items.removeFirstOrNull()
                while (currentItem != null) {
                    monkey.performInspect(currentItem)
                    reduceItemWorryLevel(currentItem)
                    val targetMonkeyIndex = if (monkey.worryTest.test(currentItem)) {
                        monkey.worryTest.trueMonkeyIndex
                    } else {
                        monkey.worryTest.falseMonkeyIndex
                    }
                    monkeys[targetMonkeyIndex].items.add(currentItem)
                    currentItem = monkey.items.removeFirstOrNull()
                }
            }
        }
    }

    private fun logMonkeysInspects(round: Int) {
        println("== After round $round ==")
        monkeys.forEachIndexed { index, monkey ->
            println("Monkey $index inspected items ${monkey.inspects} times")
        }
        println()
    }

    private fun logMonkeyItems(round: Int) {
        println("After round $round, the monkeys are holding items with these worry levels:")
        monkeys.forEachIndexed { index, monkey ->
            println("Monkey $index: ${monkey.items.joinToString(", ") { it.worryLevel.toString() }}")
        }
        println()
    }

    fun computeMonkeyBusiness(): Long = monkeys.sortedByDescending { it.inspects }
        .take(2)
        .fold(1L) { acc, monkey -> acc * monkey.inspects }

    private fun reduceItemWorryLevel(item: Item) {
        if (reduceWorryOperation != null) {
            reduceWorryOperation.invoke(item)
        } else {
            item.worryLevel = item.worryLevel % commonDivider
        }

    }
}

val defaultReduceItemWorryOperation: (Item) -> Unit = { item ->
    item.worryLevel = item.worryLevel / 3L
}

class Item(
    var worryLevel: Long
)

class WorryTest(
    val divisibleNumber: Long,
    val trueMonkeyIndex: Int,
    val falseMonkeyIndex: Int
) {
    fun test(item: Item): Boolean = item.worryLevel % divisibleNumber == 0L

}

class Monkey(
    val items: MutableList<Item> = mutableListOf(),
    val operation: (Item) -> Unit,
    val worryTest: WorryTest
) {
    var inspects = 0
        private set

    fun performInspect(item: Item) {
        operation(item)
        inspects += 1
    }
}

fun parseInput(input: String): List<Monkey> = input.split("\n\n")
    .map(::parseMonkey)

fun parseMonkey(input: String): Monkey {
    val parts = input.split("\n").map { it.trim() }
    val items = parts[1]
        .drop("Starting items: ".length)
        .split(", ")
        .map { Item(it.toLong()) }
    val operation = parts[2]
        .drop("Operation: new = ".length)
        .let { parseOperation(it) }
    val divisibleNumber = parts[3]
        .drop("Test: divisible by ".length)
        .toLong()

    val trueMonkeyIndex = parts[4]
        .drop("If true: throw to monkey ".length)
        .toInt()
    val falseMonkeyIndex = parts[5]
        .drop("If false: throw to monkey ".length)
        .toInt()
    return Monkey(
        items = items.toMutableList(),
        operation = operation,
        worryTest = WorryTest(
            divisibleNumber = divisibleNumber,
            trueMonkeyIndex = trueMonkeyIndex,
            falseMonkeyIndex = falseMonkeyIndex
        )
    )

}

fun parseOperation(input: String): (Item) -> Unit {
    val (operand1, operator, operand2) = input.split(" ")
    val operation: (Long, Long) -> Long = when (operator) {
        "+" -> Long::plus
        "*" -> Long::times
        else -> throw IllegalArgumentException("Unknown operator $operator")
    }
    val decodeOperand: (String, Item) -> Long = { text, item ->
        when (text) {
            "old" -> item.worryLevel
            else -> text.toLong()
        }
    }
    return { item ->
        item.worryLevel = operation(
            decodeOperand(operand1, item),
            decodeOperand(operand2, item)
        )
    }
}
