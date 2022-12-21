package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21Test {

    private val example = Utils.readFile("edition2022/day21-example")
    private val input = Utils.readFile("edition2022/day21-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day21().solvePart1(example)
        assertEquals(152, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day21().solvePart1(input)
        println(result)
        assertEquals(24947355373338, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day21().solvePart2(example)
        assertEquals(301, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day21().solvePart2(input)
        println(result)
        assertEquals(3876907167495, result)
    }
}
