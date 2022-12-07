package com.jdriven.riccardo.adventofcode.edition2022

import java.util.*

class Day7 {
    private val commandPrefix = "$ "

    fun solvePart1(input: String) = input
        .createFileSystem()
        .mapDirectories { it.totalSize }
        .filter { it <= 100_000 }
        .sum()

    fun solvePart2(input: String): Long {
        val fileSystem = input.createFileSystem()
        val neededSpace = 30_000_000 - fileSystem.freeDiskSpace
        return fileSystem
            .mapDirectories { it.totalSize }
            .filter { it >= neededSpace }
            .min()
    }

    private fun String.createFileSystem(): FileSystem {
        val fileSystem = FileSystem()
        parseToCommandsWithOutput().forEach { (command, output) ->
            when {
                command.startsWith("cd") -> fileSystem.cd(command.removePrefix("cd "))
                command == "ls" -> {
                    output
                        .filterNot { it.startsWith("dir") }
                        .map { it.split(' ') }
                        .forEach { (size, file) ->
                            fileSystem.pwd.files[file] = size.toLong()
                        }
                }
                else -> error("Unknown command: $command")
            }
        }

        return fileSystem
    }

    private fun String.parseToCommandsWithOutput(): List<Pair<String, List<String>>> = lineSequence()
        .fold(mutableListOf<Pair<String, MutableList<String>>>()) { acc, next ->
            if (next.startsWith(commandPrefix)) {
                acc.add(next.removePrefix(commandPrefix) to mutableListOf())
            } else {
                val (_, commandOutput) = acc.lastOrNull()!!
                commandOutput.add(next)
            }
            acc
        }
}

class FileSystem {
    private val totalDiskSize = 70_000_000
    private val root = Directory(name = "/")

    var pwd = root

    val freeDiskSpace get() = totalDiskSize - root.totalSize

    fun cd(dir: String) {
        pwd = when (dir) {
            "/" -> root
            ".." -> checkNotNull(pwd.parent)
            else -> {
                pwd.subDirectories.find { it.name == dir }
                    ?: Directory(name = dir, parent = pwd).also { pwd.subDirectories.add(it) }
            }
        }
    }

    fun <T> mapDirectories(mapper: (Directory) -> T): List<T> {
        val result = mutableListOf<T>()
        val directories = LinkedList<Directory>().also { it.add(root) }
        do {
            val dir = directories.removeLast()
            result.add(mapper(dir))
            directories.addAll(dir.subDirectories)
        } while (directories.isNotEmpty())
        return result
    }
}

data class Directory(val name: String, val parent: Directory? = null) {
    val subDirectories = mutableSetOf<Directory>()
    val files = mutableMapOf<String, Long>()

    val totalSize: Long get() = files.values.sum() + subDirectories.sumOf { it.totalSize }
}
