package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16Test {

    private val example = Utils.readFile("edition2022/day16-example")
    private val input = Utils.readFile("edition2022/day16-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day16().solvePart1(example)
        assertEquals(1651, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day16().solvePart1(input)
        println(result)
        assertEquals(2250, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day16().solvePart2(example)
        assertEquals(1707, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day16().solvePart2(input)
        println(result)
        assertEquals(3015, result)
    }
}
