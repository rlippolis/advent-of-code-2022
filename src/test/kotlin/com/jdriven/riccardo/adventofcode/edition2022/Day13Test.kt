package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13Test {

    private val example = Utils.readFile("edition2022/day13-example")
    private val input = Utils.readFile("edition2022/day13-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day13().solvePart1(example)
        assertEquals(13, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day13().solvePart1(input)
        println(result)
        assertEquals(5506, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day13().solvePart2(example)
        assertEquals(140, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day13().solvePart2(input)
        println(result)
        assertEquals(21756, result)
    }
}
