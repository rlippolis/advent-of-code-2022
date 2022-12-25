package com.jdriven.riccardo.adventofcode.edition2022

class Day25 {
    fun solvePart1(input: String): String = input.lineSequence().sumOf(SnafuEncoder::decode).let(SnafuEncoder::encode)
}

object SnafuEncoder {
    private val mapping = listOf('=', '-', '0', '1', '2')

    fun decode(snafu: String): Long = snafu.fold(0L) { acc, c -> acc * 5 + mapping.indexOf(c) - 2 }

    fun encode(digits: Long): String = buildString {
        var remainder = digits
        while (remainder > 0) {
            val num = (remainder + 2) % 5
            append(mapping[num.toInt()])
            remainder = (remainder + 2) / 5
        }
    }.reversed()
}
