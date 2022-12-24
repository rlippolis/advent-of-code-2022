package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23Test {

    private val example = Utils.readFile("edition2022/day23-example")
    private val input = Utils.readFile("edition2022/day23-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day23().solvePart1(example)
        assertEquals(110, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day23().solvePart1(input)
        println(result)
        assertEquals(4172, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day23().solvePart2(example)
        assertEquals(20, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day23().solvePart2(input)
        println(result)
        assertEquals(942, result)
    }
}
