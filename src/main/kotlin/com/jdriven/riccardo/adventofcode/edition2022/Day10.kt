package com.jdriven.riccardo.adventofcode.edition2022

class Day10 {
    fun solvePart1(input: String): Int = input.toCpuState()
        .filter { (idx, _) -> (idx + 1) in setOf(20, 60, 100, 140, 180, 220) }
        .sumOf { (idx, x) -> (idx + 1) * x }

    fun solvePart2(input: String): String = input.toCpuState()
        .map { (idx, x) -> if (idx % 40 in x-1..x+1) '#' else ' ' }
        .chunked(40)
        .joinToString(separator = "\n") { String(it.toCharArray()) }
        .dropLast(1) // LOL

    private fun String.toCpuState() = sequence {
        var x = 1
        yield(x)
        lineSequence().forEach { command ->
            when {
                command == "noop" -> yield(x)
                command.startsWith("addx") -> {
                    yield(x)
                    x += command.split(' ')[1].toInt()
                    yield(x)
                }
            }
        }
    }.withIndex()
}
