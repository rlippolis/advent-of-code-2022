package com.jdriven.riccardo.adventofcode

object Utils {
    fun readFile(name: String, trimStart: Boolean = true, trimEnd: Boolean = true): String =
        Utils::class.java.getResourceAsStream("$name.txt")!!
            .bufferedReader()
            .readText()
            .let { if (trimStart) it.trimStart() else it }
            .let { if (trimEnd) it.trimEnd() else it }

    fun IntRange.overlapsWith(other: IntRange) = (last >= other.first) && (other.last >= first)
    fun IntRange.contains(other: IntRange) = other.first in this && other.last in this
}
