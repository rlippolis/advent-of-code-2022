package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8Test {

    private val example = Utils.readFile("edition2022/day8-example")
    private val input = Utils.readFile("edition2022/day8-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day8().solvePart1(example)
        assertEquals(21, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day8().solvePart1(input)
        println(result)
        assertEquals(1820, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day8().solvePart2(example)
        assertEquals(8, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day8().solvePart2(input)
        println(result)
        assertEquals(385112, result)
    }
}
