package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

    private val example = Utils.readFile("edition2022/day3-example")
    private val input = Utils.readFile("edition2022/day3-input")

    @Test
    fun assertPriorities() {
        ('a'..'z').zip(1..26).forEach { (character, value) -> assertEquals(value, character.toPriority()) }
        ('A'..'Z').zip(27..52).forEach { (character, value) -> assertEquals(value, character.toPriority()) }
    }

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day3().solvePart1(example)
        assertEquals(157, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day3().solvePart1(input)
        println(result)
        assertEquals(8085, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day3().solvePart2(example)
        assertEquals(70, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day3().solvePart2(input)
        println(result)
        assertEquals(2515, result)
    }
}
