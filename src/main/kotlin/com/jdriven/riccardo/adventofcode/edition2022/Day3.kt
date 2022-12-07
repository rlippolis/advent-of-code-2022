package com.jdriven.riccardo.adventofcode.edition2022

class Day3 {
    fun solvePart1(input: String): Int =
        input.lineSequence()
            .map { it to (it.length / 2 - 1) }
            .map { (line, middle) -> line.slice(0..middle) to line.slice((middle + 1) until line.length) }
            .map { (first, second) -> listOf(first, second) }
            .sumOf { it.sumOfCommonPriority() }

    fun solvePart2(input: String): Int =
        input.lineSequence()
            .fold(mutableListOf(mutableListOf<String>())) { acc, next ->
                val group = acc.lastOrNull()?.takeIf { it.size < 3 } ?: mutableListOf<String>().also { acc.add(it) }
                group.add(next)
                acc
            }
            .map { (first, second, third) -> listOf(first, second, third) }
            .sumOf { it.sumOfCommonPriority() }

    private fun Collection<String>.sumOfCommonPriority() = map { it.toSet() }
        .reduce { acc, chars -> acc.intersect(chars) }
        .sumOf { common -> common.toPriority() }
}

fun Char.toPriority() = when (this) {
    in 'a'..'z' -> code - 96
    in 'A'..'Z' -> code - 38
    else -> error("Unknown char: $this")
}
