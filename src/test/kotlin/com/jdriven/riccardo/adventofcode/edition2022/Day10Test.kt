package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val example = Utils.readFile("edition2022/day10-example")
    private val input = Utils.readFile("edition2022/day10-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day10().solvePart1(example)
        assertEquals(13140, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day10().solvePart1(input)
        println(result)
        assertEquals(13440, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day10().solvePart2(example)
        println(result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day10().solvePart2(input)
        println(result)
    }
}
