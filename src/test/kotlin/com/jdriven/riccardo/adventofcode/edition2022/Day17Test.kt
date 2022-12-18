package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17Test {

    private val example = Utils.readFile("edition2022/day17-example")
    private val input = Utils.readFile("edition2022/day17-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day17().solvePart1(example)
        assertEquals(3068L, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day17().solvePart1(input)
        println(result)
        assertEquals(3177L, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day17().solvePart2(example)
        assertEquals(1514285714288, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day17().solvePart2(input)
        println(result)
        assertEquals(1565517241382, result)
    }
}
