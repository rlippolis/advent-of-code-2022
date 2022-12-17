package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Dijkstra
import java.util.*

class Day16 {
    fun solvePart1(input: String): Long = solve(parseValves(input), availableTime = 30)
    fun solvePart2(input: String): Long {
        val valves = parseValves(input)
        val shortestPaths = ShortestPaths(valves.directConnections)

        return valves.valveRates.keys.subsets().map { (v1, v2) ->
            val valves1 = valves.subset(v1)
            val valves2 = valves.subset(v2)
            solve(valves1, shortestPaths, availableTime = 26) + solve(valves2, shortestPaths, availableTime = 26)
        }.max()
    }

    private fun solve(
        valves: Valves,
        shortestPaths: ShortestPaths = ShortestPaths(valves.directConnections),
        availableTime: Int
    ): Long {
        val results = TreeSet<PathState>()
        val heuristic = hashMapOf<Pair<List<String>, Int>, Long>()
        val states = PriorityQueue<PathState>().also {
            it.add(PathState.initial(valves = valves.valveRates.keys, totalTime = availableTime))
        }

        while (states.isNotEmpty()) {
            val state = states.poll()

            if (state.done) {
                results.add(state)
            } else {
                state.remaining.forEach remaining@{ next ->
                    val pathLength = shortestPaths.get(state.currentValve, next)

                    if (pathLength?.let { it <= state.remainingTime } == true) {
                        val nextTime = state.currentTime + pathLength
                        val nextRemainingTime = availableTime - nextTime
                        val remaining = state.remaining.minus(next)
                        heuristic.compute(remaining to nextRemainingTime) { _, existing ->
                            val pressure = state.pressure + (valves.valveRates.getValue(next) * nextRemainingTime)
                            val newState = state.copy(
                                currentValve = next,
                                currentTime = nextTime,
                                pressure = pressure,
                                remaining = remaining,
                            )
                            if (existing == null || existing <= pressure) {
                                states.add(newState)
                                pressure
                            } else existing
                        }
                    }
                }
            }
        }

        return results.firstOrNull()?.pressure ?: Long.MIN_VALUE
    }

    private fun parseValves(input: String): Valves {
        val valveRates = hashMapOf<String, Long>()
        val directConnections = hashMapOf<String, Set<String>>()
        input.lineSequence()
            .map { lineRegex.matchEntire(it)!!.destructured }
            .forEach { (valve, rate, connectedValves) ->
                rate.toLong().takeIf { it > 0 }?.let { valveRates[valve] = it }
                directConnections[valve] = connectedValves.split(", ").toSet()
            }
        return Valves(valveRates = valveRates, directConnections = directConnections)
    }

    private fun <T> Collection<T>.subsets() = sequence<Pair<Set<T>, Set<T>>> {
        val l = this@subsets.size
        (0..(1 shl l)).forEach { i ->
            val left = hashSetOf<T>()
            val right = hashSetOf<T>()
            this@subsets.forEachIndexed { j, item ->
                if (i and (1 shl j) > 0) {
                    left.add(item)
                } else {
                    right.add(item)
                }
            }
            yield(left to right)
        }
    }.distinctBy { (l, r) -> setOf(l, r) }

    private val lineRegex = Regex("""Valve (.+?) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")
}

private data class Valves(
    val valveRates: Map<String, Long>,
    val directConnections: Map<String, Set<String>>,
) {
    fun subset(valveSubset: Set<String>) = copy(valveRates = valveRates.filterKeys { it in valveSubset })
}

private class ShortestPaths(private val directConnections: Map<String, Set<String>>) {
    private val cache = hashMapOf<String, MutableMap<String, Int?>>()

    fun get(from: String, to: String) = cache.computeIfAbsent(from) { hashMapOf() }.let { routes ->
        if (routes.containsKey(to)) {
            routes[to] // Could be a cached null value
        } else {
            Dijkstra.findShortestRoute(
                nodes = directConnections.keys,
                start = from,
                end = to,
                neighbourGetter = { directConnections[it]?.map { c -> c to 1 }.orEmpty() },
            )?.size.also { routes[to] = it }
        }
    }
}

private data class PathState(
    val currentValve: String,
    val currentTime: Int = 0,
    val pressure: Long = 0,
    val remaining: Collection<String>,
    val totalTime: Int,
): Comparable<PathState> {
    val remainingTime get() = totalTime - currentTime

    val done get() = currentTime == totalTime || remaining.isEmpty()

    override fun compareTo(other: PathState): Int = compareValues(other.pressure, pressure)

    companion object {
        fun initial(valves: Collection<String>, totalTime: Int) = PathState(
            currentValve = "AA",
            remaining = valves,
            totalTime = totalTime,
        )
    }
}
