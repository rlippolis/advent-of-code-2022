package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val example = Utils.readFile("edition2022/day11-example")
    private val input = Utils.readFile("edition2022/day11-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day11().solvePart1(example)
        assertEquals(10605, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day11().solvePart1(input)
        println(result)
        assertEquals(98280, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day11().solvePart2(example)
        assertEquals(2713310158, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day11().solvePart2(input)
        println(result)
        assertEquals(17673687232, result)
    }
}
