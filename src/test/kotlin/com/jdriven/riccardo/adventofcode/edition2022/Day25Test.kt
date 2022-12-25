package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Day25Test {

    private val example = Utils.readFile("edition2022/day25-example")
    private val input = Utils.readFile("edition2022/day25-input")

    @MethodSource("encodings")
    @ParameterizedTest
    fun `should properly encode`(digits: Long, snafu: String) {
        assertEquals(snafu, SnafuEncoder.encode(digits))
    }

    @MethodSource("encodings")
    @ParameterizedTest
    fun `should properly decode`(digits: Long, snafu: String) {
        assertEquals(digits, SnafuEncoder.decode(snafu))
    }

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day25().solvePart1(example)
        assertEquals("2=-1=0", result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day25().solvePart1(input)
        println(result)
        assertEquals("2-=2-0=-0-=0200=--21", result)
    }

    companion object {
        @JvmStatic
        fun encodings() = listOf(
            "1" to "1",
            "2" to "2",
            "3" to "1=",
            "4" to "1-",
            "5" to "10",
            "6" to "11",
            "7" to "12",
            "8" to "2=",
            "9" to "2-",
            "10" to "20",
            "15" to "1=0",
            "20" to "1-0",
            "2022" to "1=11-2",
            "12345" to "1-0---0",
            "314159265" to "1121-1110-1=0",
        ).map { (digits, snafu) -> Arguments.of(digits, snafu) }.stream()
    }
}
