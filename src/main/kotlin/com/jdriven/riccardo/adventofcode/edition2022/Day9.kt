package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.edition2022.RopeDirection.*
import kotlin.math.absoluteValue
import kotlin.math.sign

class Day9 {
    fun solvePart1(input: String): Int = input.solve(ropeSize = 2)

    fun solvePart2(input: String): Int = input.solve(ropeSize = 10)

    private fun String.solve(ropeSize: Int) = lineSequence()
        .map { line -> line.split(' ') }
        .flatMap { (direction, count) -> (1..count.toInt()).map { direction.toDirection() } }
        .fold(mutableSetOf(RopePosition(x = 0, y = 0)) to rope(ropeSize)) { (visited, rope), direction ->
            val newRope = rope.move2(direction)
            visited.also { it.add(newRope.last()) } to newRope
        }.first.size

    private fun rope(size: Int) = List(size) { RopePosition(x = 0, y = 0) }

    private fun List<RopePosition>.move2(direction: RopeDirection) = first().move(direction).let { head ->
        drop(1).runningFold(head) { previous, next -> next.moveTowards(previous) }
    }

    private fun String.toDirection() = when (this) {
        "U" -> Up
        "D" -> Down
        "L" -> Left
        "R" -> Right
        else -> error("Unknown direction: $this")
    }
}

private data class RopePosition(val x: Int, val y: Int) {
    fun move(direction: RopeDirection) = when (direction) {
        Up -> copy(y = y + 1)
        Down -> copy(y = y - 1)
        Left -> copy(x = x - 1)
        Right -> copy(x = x + 1)
    }

    fun moveTowards(other: RopePosition) =
        if (isNear(other)) this else copy(x = x + (other.x - x).sign, y = y + (other.y - y).sign)

    private fun isNear(other: RopePosition) = (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1
}

private enum class RopeDirection {
    Up,
    Down,
    Left,
    Right
}
