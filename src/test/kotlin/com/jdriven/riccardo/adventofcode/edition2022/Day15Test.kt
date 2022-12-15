package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15Test {

    private val example = Utils.readFile("edition2022/day15-example")
    private val input = Utils.readFile("edition2022/day15-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day15().solvePart1(example, 10)
        assertEquals(26, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day15().solvePart1(input, 2000000)
        println(result)
        assertEquals(5127797, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day15().solvePart2(example, 20)
        assertEquals(56000011L, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day15().solvePart2(input, 4000000)
        println(result)
        assertEquals(12518502636475L, result)
    }
}
