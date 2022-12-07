package com.jdriven.riccardo.adventofcode.edition2022

class Day1 {
    fun solvePart1(input: String): Int =
        input.groupElves().maxOfOrNull { it.sum() } ?: 0

    fun solvePart2(input: String): Int =
        input.groupElves().sortedByDescending { it.sum() }.take(3).sumOf { it.sum() }

    private fun String.groupElves() =
        lineSequence()
            .map { it.toIntOrNull() }
            .fold(mutableListOf(mutableListOf<Int>())) { acc, next ->
                next?.let { acc.last().add(it) } ?: acc.add(mutableListOf())
                acc
            }
}
