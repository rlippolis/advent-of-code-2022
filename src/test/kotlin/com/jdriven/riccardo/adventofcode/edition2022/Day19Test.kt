package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19Test {

    private val example = Utils.readFile("edition2022/day19-example")
    private val input = Utils.readFile("edition2022/day19-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day19().solvePart1(example)
        assertEquals(33, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day19().solvePart1(input)
        println(result)
        assertEquals(1834, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day19().solvePart2(example)
        assertEquals(56 * 62, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day19().solvePart2(input)
        println(result)
        assertEquals(2240, result)
    }
}
