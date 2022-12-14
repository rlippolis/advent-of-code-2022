package com.jdriven.riccardo.adventofcode.edition2022

import kotlin.math.sign

class Day14 {
    fun solvePart1(input: String): Int = Cave(input, hasFloor = false).fillWithSand()
    fun solvePart2(input: String): Int = Cave(input, hasFloor = true).fillWithSand()

    private fun Cave.fillWithSand() = generateSequence { dropSand() }.takeWhile { it }.count()
}

private class Cave private constructor(private val occupiedPoints: MutableSet<CavePoint>, private val hasFloor: Boolean) {
    private val maxY = occupiedPoints.maxOf { it.y }

    tailrec fun dropSand(point: CavePoint = CavePoint(x = 500, y = 0)): Boolean = when {
        !hasFloor && !point.isInRange() -> false
        !point.down().isOccupied() -> dropSand(point.down())
        !point.downLeft().isOccupied() -> dropSand(point.downLeft())
        !point.downRight().isOccupied() -> dropSand(point.downRight())
        else -> occupiedPoints.add(point)
    }

    private fun CavePoint.isInRange() = y <= maxY
    private fun CavePoint.isOccupied() = this in occupiedPoints || (hasFloor && this.y == maxY + 2)

    companion object {
        operator fun invoke(input: String, hasFloor: Boolean) = input.lineSequence()
            .map { it.split(" -> ").asSequence() }
            .map { it.map { point -> point.toCavePoint() } }
            .flatMap { it.zipWithNext() }
            .flatMap { (from, to) -> from until to }
            .toHashSet()
            .let { cavePoints -> Cave(occupiedPoints = cavePoints, hasFloor = hasFloor) }

        private fun String.toCavePoint() = split(',').let { (x, y) -> CavePoint(x = x.toInt(), y = y.toInt()) }

        private infix fun CavePoint.until(to: CavePoint): Sequence<CavePoint> = sequence {
            var current = this@until
            yield(current)
            while (current != to) {
                current = current.towards(to)
                yield(current)
            }
        }
    }
}

private data class CavePoint(val x: Int, val y: Int) {
    fun down() = copy(y = y + 1)
    fun downLeft() = copy(x = x - 1, y = y + 1)
    fun downRight() = copy(x = x + 1, y = y + 1)
    fun towards(other: CavePoint) = copy(x = x + (other.x - x).sign, y = y + (other.y - y).sign)
}
