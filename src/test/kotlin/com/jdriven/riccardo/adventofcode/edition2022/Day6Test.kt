package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day6Test {

    private val input = Utils.readFile("edition2022/day6-input")

    @CsvSource(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb,7",
        "bvwbjplbgvbhsrlpgdmjqwftvncz,5",
        "nppdvjthqldpwncqszvftbrmjlhg,6",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11",
    )
    @ParameterizedTest
    fun shouldSolveExamplePart1(input: String, expected: Int) {
        val result = Day6().solvePart1(input)
        assertEquals(expected, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day6().solvePart1(input)
        println(result)
        assertEquals(1640, result)
    }

    @CsvSource(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb,19",
        "bvwbjplbgvbhsrlpgdmjqwftvncz,23",
        "nppdvjthqldpwncqszvftbrmjlhg,23",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26",
    )
    @ParameterizedTest
    fun shouldSolveExamplePart2(input: String, expected: Int) {
        val result = Day6().solvePart2(input)
        assertEquals(expected, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day6().solvePart2(input)
        println(result)
        assertEquals(3613, result)
    }
}
