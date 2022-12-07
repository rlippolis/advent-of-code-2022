package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day4Test {

    private val example = Utils.readFile("edition2022/day4-example")
    private val input = Utils.readFile("edition2022/day4-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day4().solvePart1(example)
        assertEquals(2, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day4().solvePart1(input)
        println(result)
        assertEquals(498, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day4().solvePart2(example)
        assertEquals(4, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day4().solvePart2(input)
        println(result)
        assertEquals(859, result)
    }
}
