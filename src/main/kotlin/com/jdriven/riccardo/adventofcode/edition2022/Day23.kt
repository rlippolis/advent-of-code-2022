package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.edition2022.ElfDirection.*

class Day23 {
    fun solvePart1(input: String): Int =
        (0 until 10).fold(input.toElves()) { e, i -> e.doRound(i) }.countEmptyGroundTiles()

    fun solvePart2(input: String): Int = generateSequence(input.toElves() to 0) { (previous, round) ->
        previous.doRound(round).takeIf { it != previous }?.let { it to round + 1 }
    }.last().second + 1

    private fun String.toElves() = lineSequence()
        .flatMapIndexed { row, line ->
            line.mapIndexedNotNull { col, point ->
                point.takeIf { it == '#' }
                    ?.let { ElfPoint(row = row, col = col) }
            }
        }.toSet()

    private fun Set<ElfPoint>.doRound(roundNr: Int) = fold(mutableSetOf<Pair<ElfPoint, ElfPoint>>()) { acc, elf ->
        acc.apply {
            val allDirections = directionsForRound(roundNr).associateWith { elf.positionsToCheck(it) }
            val nextPosition = if (allDirections.values.all { it.none { p -> p in this@doRound } }) elf else {
                allDirections.entries.firstOrNull { (_, positions) -> positions.none { it in this@doRound } }
                    ?.let { (dir, _) -> elf.move(dir) }
                    ?: elf
            }
            add(nextPosition to elf)
        }
    }.groupBy(keySelector = { it.first }, valueTransform = { it.second }).entries.partition { it.value.size == 1 }
        .let { (valid, invalid) -> valid.map { it.key } + invalid.flatMap { it.value } }
        .toSet()

    private fun directionsForRound(roundNr: Int): List<ElfDirection> {
        val directions = ElfDirection.values()
        val start = roundNr % directions.size
        return directions.indices.map { directions[(it + start) % directions.size] }
    }

    private fun Set<ElfPoint>.countEmptyGroundTiles(): Int {
        val rowRange = minOf { it.row }..maxOf { it.row }
        val colRange = minOf { it.col }..maxOf { it.col }
        return rowRange.sumOf { row -> colRange.count { col -> ElfPoint(row = row, col = col) !in this } }
    }
}

private data class ElfPoint(val row: Int, val col: Int) {

    fun positionsToCheck(direction: ElfDirection) = when (direction) {
        North -> listOf(
            copy(row = row - 1, col = col - 1),
            copy(row = row - 1),
            copy(row = row - 1, col = col + 1),
        )
        South -> listOf(
            copy(row = row + 1, col = col - 1),
            copy(row = row + 1),
            copy(row = row + 1, col = col + 1),
        )
        West -> listOf(
            copy(row = row - 1, col = col - 1),
            copy(col = col - 1),
            copy(row = row + 1, col = col - 1),
        )
        East -> listOf(
            copy(row = row - 1, col = col + 1),
            copy(col = col + 1),
            copy(row = row + 1, col = col + 1),
        )
    }

    fun move(direction: ElfDirection) = when (direction) {
        North -> copy(row = row - 1)
        South -> copy(row = row + 1)
        West -> copy(col = col - 1)
        East -> copy(col = col + 1)
    }
}

private enum class ElfDirection {
    North,
    South,
    West,
    East
}
