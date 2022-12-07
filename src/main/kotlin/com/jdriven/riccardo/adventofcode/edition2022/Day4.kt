package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils.contains
import com.jdriven.riccardo.adventofcode.Utils.overlapsWith

class Day4 {
    fun solvePart1(input: String): Int = input.toRangePairs().count { (first, second) ->
        first.contains(second) || second.contains(first)
    }

    fun solvePart2(input: String): Int = input.toRangePairs().count { (first, second) ->
        first.overlapsWith(second)
    }

    private fun String.toRangePairs() = lineSequence()
        .map { it.split(',') }
        .map { (first, second) -> first.toRange() to second.toRange() }

    private fun String.toRange() = split('-').let { (low, high) -> IntRange(low.toInt(), high.toInt()) }
}
