package ua.olehlypskyi.adventofcode.seventh

import java.io.File

const val TOTAL_SIZE = 70_000_000
const val UPDATE_SIZE = 30_000_000

fun main() {
    val input = File("./input.txt").readText()
    val console = Console()
    parseInput(input, console)

    val sum = console.sizeTable.filter {
        it.key != console.rootDir && it.value <= 100_000
    }
        .toList()
        .sumOf { it.second }

    val miDirForUpdateSize = getDirToDeleteSize(console)

    print(miDirForUpdateSize)
}

fun getDirToDeleteSize(console: Console): Int {
    val usedSpace = console.sizeTable[console.rootDir] ?: 0
    val freeSpace = TOTAL_SIZE - usedSpace
    return console.sizeTable.filter { freeSpace + it.value >= UPDATE_SIZE }
        .minBy { it.value }
        .value
}


sealed class Node(val name: String, var parent: Dir?) {
    class File(name: String, parent: Dir?, val size: Int) : Node(name, parent)
    class Dir(
        name: String,
        parent: Dir?,
        val children: MutableList<Node> = mutableListOf()
    ) : Node(name, parent) {
        fun addChildNode(node: Node) {
            children.add(node)
            node.parent = this
        }

    }
}

const val ROOT_DIR_NAME = "/"
const val OUT_DIR = ".."
const val DIR_IDENTIFIER = "dir"
const val COMMAND_IDENTIFIER = "$"


class Console {
    sealed class Command {
        object ls : Command()
        class cd(val dirName: String) : Command()
    }

    val sizeTable = mutableMapOf<Node.Dir, Int>()
    val rootDir: Node.Dir = Node.Dir(name = ROOT_DIR_NAME, parent = null)
    var currentDir: Node.Dir = rootDir
    var currentCommand: Command? = null

    fun processLine(line: String, lineNumber: Int) {
        if (line.startsWith(COMMAND_IDENTIFIER)) {
            // Run command
            val command = parseCommandLine(line)
            runCommand(command, lineNumber)
        } else {
            // Handle command output
            when (currentCommand) {
                Command.ls -> {
                    val node = parseLsLine(line)
                    currentDir.addChildNode(node)
                }

                else -> throw IllegalStateException("Illegal input line $line")
            }
        }
    }

    private fun runCommand(command: Command, lineNumber: Int) {
        currentCommand = command
        when (command) {
            is Command.ls -> Unit
            is Command.cd -> currentDir = when (command.dirName) {
                OUT_DIR ->
                    currentDir.parent
                        ?: throw IllegalArgumentException("Cannot find parent dir in ${currentDir.name} at $lineNumber")

                ROOT_DIR_NAME -> rootDir
                else -> findDir(command.dirName)
                    ?: throw IllegalArgumentException("Cannot find dir ${command.dirName} in ${currentDir.name} at $lineNumber")
            }
        }
    }

    private fun findDir(name: String): Node.Dir? = currentDir.children
        .firstNotNullOfOrNull { node -> (node as? Node.Dir)?.takeIf { it.name == name } }
}

fun parseInput(input: String, console: Console) {
    input.split("\n").forEachIndexed { index, line ->
        console.processLine(line, index)
    }
    fillDirSizeTable(console.rootDir, console.sizeTable)
}

fun parseCommandLine(line: String): Console.Command {
    val parts = line.split(" ")
    return when (parts[1]) {
        "ls" -> Console.Command.ls
        "cd" -> Console.Command.cd(parts[2])
        else -> throw IllegalArgumentException("Unknown command $line")
    }
}

fun parseLsLine(line: String): Node {
    val parts = line.split(" ")
    return if (parts[0] == DIR_IDENTIFIER) {
        val dirName = parts[1]
        Node.Dir(name = dirName, parent = null)
    } else {
        val size = parts[0].toInt()
        val fileName = parts[1]
        Node.File(name = fileName, parent = null, size = size)
    }
}

fun fillDirSizeTable(rootDir: Node.Dir, table: MutableMap<Node.Dir, Int>): Int {
    var currentSize: Int = 0
    rootDir.children.forEach { node ->
        currentSize += when (node) {
            is Node.File -> node.size
            is Node.Dir -> fillDirSizeTable(node, table)
        }
    }
    table[rootDir] = currentSize
    return currentSize
}
