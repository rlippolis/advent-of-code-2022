package com.jdriven.riccardo.adventofcode

object Utils {
    fun readFile(name: String, trimStart: Boolean = true, trimEnd: Boolean = true): String =
        Utils::class.java.getResourceAsStream("$name.txt")!!
            .bufferedReader()
            .readText()
            .let { if (trimStart) it.trimStart() else it }
            .let { if (trimEnd) it.trimEnd() else it }
}
