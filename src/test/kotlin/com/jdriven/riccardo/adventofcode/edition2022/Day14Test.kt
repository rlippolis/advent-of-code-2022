package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {

    private val example = Utils.readFile("edition2022/day14-example")
    private val input = Utils.readFile("edition2022/day14-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day14().solvePart1(example)
        assertEquals(24, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day14().solvePart1(input)
        println(result)
        assertEquals(614, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day14().solvePart2(example)
        assertEquals(93, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day14().solvePart2(input)
        println(result)
        assertEquals(26170, result)
    }
}
