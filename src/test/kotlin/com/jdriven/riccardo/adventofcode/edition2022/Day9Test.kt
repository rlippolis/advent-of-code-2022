package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day9Test {

    private val examplePart1 = Utils.readFile("edition2022/day9-example-part1")
    private val examplePart2 = Utils.readFile("edition2022/day9-example-part2")
    private val input = Utils.readFile("edition2022/day9-input")

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day9().solvePart1(examplePart1)
        assertEquals(13, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day9().solvePart1(input)
        println(result)
        assertEquals(6090, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day9().solvePart2(examplePart2)
        assertEquals(36, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day9().solvePart2(input)
        println(result)
        assertEquals(2566, result)
    }
}
