package com.jdriven.riccardo.adventofcode.edition2022

class Day6 {
    fun solvePart1(input: String): Int = input.findStartOfMessageMarker(4)

    fun solvePart2(input: String): Int = input.findStartOfMessageMarker(14)

    private fun String.findStartOfMessageMarker(size: Int) =
        windowedSequence(size) { it.toSet().size }.indexOfFirst { it == size } + size
}
