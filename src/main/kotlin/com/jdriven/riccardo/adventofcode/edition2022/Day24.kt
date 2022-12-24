package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.edition2022.BlizzardDirection.*

class Day24 {
    fun solvePart1(input: String): Int = with(input.toValley()) { shortestRoute(start, end) }

    fun solvePart2(input: String): Int = with(input.toValley()) {
        shortestRoute(start, end, shortestRoute(end, start, shortestRoute(start, end)))
    }

    private fun Valley.shortestRoute(from: ValleyLocation, to: ValleyLocation, startTime: Int = 0): Int {
        val states = ArrayDeque<ValleySearchState>()
        states.add(ValleySearchState(minute = startTime, location = from))

        val visited = mutableSetOf<ValleySearchState>()
        var shortestTime: Int = Integer.MAX_VALUE
        while (states.isNotEmpty()) {
            val state = states.removeFirst()
            if (state.location == to) {
                shortestTime = shortestTime.coerceAtMost(state.minute)
            } else if (state.minute < shortestTime) {
                state.location.moves.forEach { next ->
                    if (isValidAt(next, state.minute + 1)) {
                        val nextState = state.copy(minute = state.minute + 1, location = next)
                        if (visited.add(nextState)) {
                            states.add(nextState)
                        }
                    }
                }
            }
        }

        return shortestTime
    }

    private fun String.toValley() = lines().let { rows ->
        Valley(
            start = rows.first().indexOfFirst { it == '.' }.let { ValleyLocation(row = 0, col = it) },
            end = rows.last().indexOfFirst { it == '.' }.let { ValleyLocation(row = rows.lastIndex, col = it) },
            blizzards = rows.flatMapIndexed { rowIdx, row ->
                row.mapIndexedNotNull { colIdx, c ->
                    when (c) {
                        '^' -> Up
                        'v' -> Down
                        '<' -> Left
                        '>' -> Right
                        else -> null
                    }?.let { ValleyLocation(rowIdx, colIdx) to it }
                }
            }.toSet(),
            rows = rows.size,
            cols = rows.first().length,
        )
    }
}

private data class ValleyLocation(val row: Int, val col: Int) {
    val moves get() = listOf(
        copy(row = row - 1),
        copy(row = row + 1),
        copy(col = col - 1),
        copy(col = col + 1),
        this
    )
}

private class Valley(
    val start: ValleyLocation,
    val end: ValleyLocation,
    blizzards: Set<Pair<ValleyLocation, BlizzardDirection>>,
    val rows: Int,
    val cols: Int,
) {
    private val minutesMod = lcm(rows - 2, cols - 2)

    private val blizzards: Map<Int, Set<ValleyLocation>> = (0..minutesMod).associateWith { time ->
        blizzards.mapTo(hashSetOf()) { (loc, dir) ->
            when (dir) {
                Up -> loc.copy(row = ((loc.row - 1 - time).mod(rows - 2)) + 1)
                Down -> loc.copy(row = ((loc.row - 1 + time).mod(rows - 2)) + 1)
                Left -> loc.copy(col = ((loc.col - 1 - time).mod(cols - 2)) + 1)
                Right -> loc.copy(col = ((loc.col - 1 + time).mod(cols - 2)) + 1)
            }
        }
    }

    fun isValidAt(loc: ValleyLocation, minute: Int) =
        ((loc == start || loc == end) || (loc.row in 1..rows - 2 && loc.col in 1..cols - 2)) &&
                loc !in blizzards.getValue(minute.mod(minutesMod))

    private tailrec fun lcm(n1: Int, n2: Int, lcm: Int = n1.coerceAtLeast(n2)): Int = when {
        lcm % n1 == 0 && lcm % n2 == 0 -> lcm
        else -> lcm(n1, n2, lcm + 1)
    }
}

private data class ValleySearchState(val minute: Int, val location: ValleyLocation)

private enum class BlizzardDirection {
    Up,
    Down,
    Left,
    Right
}
