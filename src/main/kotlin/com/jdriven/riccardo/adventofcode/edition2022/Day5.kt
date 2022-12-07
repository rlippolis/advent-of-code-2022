package com.jdriven.riccardo.adventofcode.edition2022

import java.util.*

class Day5 {
    fun solvePart1(input: String) = processCrates(input, CrateMover9000)

    fun solvePart2(input: String) = processCrates(input, CrateMover9001)

    private fun processCrates(input: String, crateMover: CrateMover): String {
        val state = Stacks(crateMover)
        input.lineSequence().forEach { line ->
            when {
                line.contains('[') -> processCrateLine(line, state)
                line.startsWith("move") -> parseMoveLine(line).let { (times, from, to) ->
                    state.moveCrates(times, from, to)
                }
            }
        }

        state.print()
        return state.topCrates
    }

    private fun processCrateLine(line: String, state: Stacks) {
        line.chunked(4)
            .withIndex()
            .filter { (_, crate) -> crate.isNotBlank() }
            .forEach { (idx, crate) -> state.insert(idx, crate.trim()[1]) }
    }

    private fun parseMoveLine(line: String) = moveRegex.matchEntire(line)!!.destructured
        .let {
            val (times, from, to) = it
            Triple(times.toInt(), from.toInt() - 1, to.toInt() - 1)
        }

    private val moveRegex = Regex("""move (\d+) from (\d+) to (\d+)""")
}

class Stacks(private val crateMover: CrateMover) {
    private val stacks = mutableListOf<LinkedList<Char>>()

    val topCrates
        get() = buildString {
            stacks.forEach { it.peekLast()?.let { crate -> append(crate) } }
        }

    fun insert(stackIndex: Int, crate: Char) {
        while (stacks.size < stackIndex + 1) stacks.add(LinkedList())
        stacks[stackIndex].addFirst(crate)
    }

    fun print() {
        stacks.forEachIndexed { index, stack ->
            print("${index + 1}: ")
            stack.forEach { crate -> print("[$crate] ") }
            println()
        }
    }

    fun moveCrates(amount: Int, fromIdx: Int, toIdx: Int) {
        crateMover.moveCrates(stacks, amount, fromIdx, toIdx)
    }
}

interface CrateMover {
    fun moveCrates(stacks: MutableList<LinkedList<Char>>, amount: Int, fromIdx: Int, toIdx: Int)
}

object CrateMover9000: CrateMover {
    override fun moveCrates(stacks: MutableList<LinkedList<Char>>, amount: Int, fromIdx: Int, toIdx: Int) {
        repeat(amount) {
            val crate = stacks[fromIdx].pollLast()
            stacks[toIdx].add(crate)
        }
    }
}

object CrateMover9001: CrateMover {
    override fun moveCrates(stacks: MutableList<LinkedList<Char>>, amount: Int, fromIdx: Int, toIdx: Int) {
        val crates = stacks[fromIdx].takeLast(amount)
        repeat(amount) { stacks[fromIdx].removeLast() }
        stacks[toIdx].addAll(crates)
    }
}
