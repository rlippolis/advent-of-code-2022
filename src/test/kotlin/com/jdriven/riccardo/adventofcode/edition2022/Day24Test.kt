package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24Test {

    private val example = Utils.readFile("edition2022/day24-example")
    private val input = Utils.readFile("edition2022/day24-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day24().solvePart1(example)
        assertEquals(18, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day24().solvePart1(input)
        println(result)
        assertEquals(326, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day24().solvePart2(example)
        assertEquals(54, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day24().solvePart2(input)
        println(result)
        assertEquals(976, result)
    }
}
