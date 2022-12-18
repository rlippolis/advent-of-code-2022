package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {

    private val example = Utils.readFile("edition2022/day18-example")
    private val input = Utils.readFile("edition2022/day18-input")

    @Test
    fun shouldSolveSimpleExample() {
        val result = Day18().solvePart1("""
            1,1,1
            2,1,1
        """.trimIndent())
        assertEquals(10, result)
    }

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day18().solvePart1(example)
        assertEquals(64, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day18().solvePart1(input)
        println(result)
        assertEquals(4320, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day18().solvePart2(example)
        assertEquals(58, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day18().solvePart2(input)
        println(result)
        assertEquals(2456, result)
    }
}
