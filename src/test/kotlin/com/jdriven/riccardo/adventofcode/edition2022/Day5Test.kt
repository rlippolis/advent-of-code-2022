package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {

    private val example = Utils.readFile("edition2022/day5-example", trimStart = false)
    private val input = Utils.readFile("edition2022/day5-input", trimStart = false)

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day5().solvePart1(example)
        assertEquals("CMZ", result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day5().solvePart1(input)
        println(result)
        assertEquals("ZBDRNPMVH", result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day5().solvePart2(example)
        assertEquals("MCD", result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day5().solvePart2(input)
        println(result)
        assertEquals("WDLPFNNNB", result)
    }
}
