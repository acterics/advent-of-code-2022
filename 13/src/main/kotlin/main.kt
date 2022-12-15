package ua.olehlypskyi.adventofcode.thirteen

import java.io.File
import java.lang.Integer.max

val additionalData = listOf(
    PacketData.ValueData(2).wrapToList().wrapToList(),
    PacketData.ValueData(6).wrapToList().wrapToList()
)

fun main() {
    val input = File("./input.txt").readText()
    val pairs = parseInput(input)
    val key = computeDecoderKey(pairs)
    println(key)
}

fun computeDecoderKey(pairs: List<PacketPair>) = (pairs.flatMap { listOf(it.left, it.right) } + additionalData)
    .sortedWith(ListDataComparator.reversed())
    .mapIndexed { index, listData -> index to listData }
    .filter { (_, data) -> additionalData.contains(data) }
    .fold(1) { acc, (index, _) -> acc * (index + 1) }

fun sumRightOrderIndices(pairs: List<PacketPair>): Int = pairs
    .mapIndexed { index, packetPair ->
        index to packetPair
    }
    .filter { (_, pair) -> pair.getOrderResult() == OrderResult.RIGHT }
    .sumOf { (index, _) -> index + 1 }

sealed class PacketData {
    data class ListData(private val values: List<PacketData>) : PacketData(), List<PacketData> by values
    data class ValueData(val value: Int) : PacketData()
}

object ListDataComparator : Comparator<PacketData.ListData> {
    override fun compare(a: PacketData.ListData, b: PacketData.ListData): Int {
        return when (PacketPair(a, b).getOrderResult()) {
            OrderResult.RIGHT -> 1
            OrderResult.NOT_RIGHT -> -1
            OrderResult.UNDEFINED -> 0
        }
    }
}

enum class OrderResult {
    RIGHT,
    NOT_RIGHT,
    UNDEFINED;
}

class PacketPair(
    val left: PacketData.ListData,
    val right: PacketData.ListData
) {
    fun getOrderResult(): OrderResult {
        (0 until max(left.size, right.size)).forEach { i ->
            val leftData = left.getOrNull(i) ?: return OrderResult.RIGHT
            val rightData = right.getOrNull(i) ?: return OrderResult.NOT_RIGHT
            when {
                leftData is PacketData.ValueData && rightData is PacketData.ValueData -> {
                    when {
                        leftData.value < rightData.value -> return OrderResult.RIGHT
                        leftData.value > rightData.value -> return OrderResult.NOT_RIGHT
                    }
                }

                leftData is PacketData.ListData && rightData is PacketData.ListData -> {
                    val pair = PacketPair(leftData, rightData)
                    val pairOrderResult = pair.getOrderResult()
                    if (pairOrderResult != OrderResult.UNDEFINED) {
                        return pairOrderResult
                    }
                }

                leftData is PacketData.ListData && rightData is PacketData.ValueData -> {
                    val pair = PacketPair(leftData, PacketData.ListData(listOf(rightData)))
                    val pairOrderResult = pair.getOrderResult()
                    if (pairOrderResult != OrderResult.UNDEFINED) {
                        return pairOrderResult
                    }
                }

                leftData is PacketData.ValueData && rightData is PacketData.ListData -> {
                    val pair = PacketPair(PacketData.ListData(listOf(leftData)), rightData)
                    val pairOrderResult = pair.getOrderResult()
                    if (pairOrderResult != OrderResult.UNDEFINED) {
                        return pairOrderResult
                    }
                }
            }
        }
        return OrderResult.UNDEFINED
    }
}


fun parseInput(input: String): List<PacketPair> = input.split("\n\n")
    .map { packetPairInput ->
        val (left, right) = packetPairInput.split("\n").map(::parsePacketListData)
        PacketPair(left, right)
    }

fun parsePacketListData(input: String): PacketData.ListData {
    val values = mutableListOf<PacketData>()
    require(input[0] == '[') {
        "List should start with '['"
    }
    var offset = 1
    var currentNumber = ""
    while (input[offset] != ']') {
        when (val symbol = input[offset]) {
            '[' -> {
                val list = parsePacketListData(input.substring(offset))
                values.add(list)
                offset += list.getOffset()
            }

            ',' -> {
                if (currentNumber.isNotEmpty()) {
                    values.add(PacketData.ValueData(currentNumber.toInt()))
                    currentNumber = ""
                }
                offset += 1
            }

            in ('0'..'9') -> {
                currentNumber += symbol
                offset += 1
            }

            else -> throw IllegalArgumentException("Illegal symbol $symbol")
        }
    }
    if (currentNumber.isNotEmpty()) {
        values.add(PacketData.ValueData(currentNumber.toInt()))
    }

    return PacketData.ListData(values)
}

fun PacketData.wrapToList(): PacketData.ListData = PacketData.ListData(
    listOf(this)
)

fun PacketData.ListData.getOffset(): Int {
    var offset: Int = 2 // []
    forEachIndexed { index, data ->
        offset += when (data) {
            is PacketData.ValueData -> 1
            is PacketData.ListData -> data.getOffset()
        }
        if (index < size - 1) {
            offset += 1 // ,
        }
    }
    return offset
}