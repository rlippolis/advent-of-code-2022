package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import com.jdriven.riccardo.adventofcode.edition2022.Hand.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class Day2Test {

    private val example = Utils.readFile("edition2022/day2-example")
    private val input = Utils.readFile("edition2022/day2-input")

    @Test
    fun assertGameRules() {
        assertTrue(Rock > Scissors)
        assertTrue(Scissors > Paper)
        assertTrue(Paper > Rock)

        assertEquals(Scissors, Rock.handItBeats)
        assertEquals(Paper, Scissors.handItBeats)
        assertEquals(Rock, Paper.handItBeats)

        assertEquals(Paper, Rock.handThatBeatsIt)
        assertEquals(Rock, Scissors.handThatBeatsIt)
        assertEquals(Scissors, Paper.handThatBeatsIt)
    }

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day2().solvePart1(example)
        assertEquals(15, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day2().solvePart1(input)
        println(result)
        assertEquals(14297, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day2().solvePart2(example)
        assertEquals(12, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day2().solvePart2(input)
        println(result)
        assertEquals(10498, result)
    }
}
