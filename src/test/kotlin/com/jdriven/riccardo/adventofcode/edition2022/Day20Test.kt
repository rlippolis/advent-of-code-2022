package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20Test {

    private val example = Utils.readFile("edition2022/day20-example")
    private val input = Utils.readFile("edition2022/day20-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day20().solvePart1(example)
        assertEquals(3, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day20().solvePart1(input)
        println(result)
        assertEquals(6387, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day20().solvePart2(example)
        assertEquals(1623178306, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day20().solvePart2(input)
        println(result)
        assertEquals(2455057187825, result)
    }
}
