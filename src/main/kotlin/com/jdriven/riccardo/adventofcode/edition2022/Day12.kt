package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Dijkstra

class Day12 {
    fun solvePart1(input: String): Int = HeightMap.create(input)
        .findShortestRoute()
        ?.drop(1) // # of steps == # of points - 1
        ?.size ?: error("No shortest route found")

    fun solvePart2(input: String): Int = HeightMap.create(input).let { heightMap ->
        heightMap
            .map { point, char -> point.takeIf { char == 'a' && it != heightMap.start } }
            .filterNotNull()
            .mapNotNull { heightMap.findShortestRoute(it) }
            .minOf { it.size - 1 }
    }
}

private class HeightMap(private val grid: Array<CharArray>, val start: HeightPoint, val end: HeightPoint) {

    private val rowIndices = grid.indices
    private val columnIndices = grid[0].indices

    fun <T> map(transform: (HeightPoint, Char) -> T): List<T> =
        rowIndices.flatMap { rowIdx ->
            columnIndices.map { colIdx ->
                HeightPoint(rowIdx = rowIdx, colIdx = colIdx).let { point -> transform(point, get(point)) }
            }
        }

    private fun get(point: HeightPoint) = grid[point.rowIdx][point.colIdx]

    fun findShortestRoute(startingPoint: HeightPoint = start) = Dijkstra.findShortestRoute(
        nodes = rowIndices.flatMap { rowIdx -> columnIndices.map { colIdx -> HeightPoint(rowIdx = rowIdx, colIdx = colIdx) } },
        start = startingPoint,
        end = end,
        neighbourGetter = ::getTraversableNeighbours
    )

    private fun getTraversableNeighbours(point: HeightPoint): List<Pair<HeightPoint, Int>> {
        val currentHeight = get(point)
        val (rowIdx, colIdx) = point
        return listOfNotNull(
            HeightPoint(rowIdx = rowIdx - 1, colIdx = colIdx).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx + 1, colIdx = colIdx).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx, colIdx = colIdx - 1).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx, colIdx = colIdx + 1).takeIf { canTravelTo(it, currentHeight) },
        ).map { it to 1 } // Constant weight of 1
    }

    private fun canTravelTo(point: HeightPoint, fromHeight: Char): Boolean =
        point.rowIdx in rowIndices && point.colIdx in columnIndices && get(point).code <= fromHeight.code + 1

    companion object {
        fun create(input: String) = input.lines().let { lines ->
            var start: HeightPoint? = null
            var end: HeightPoint? = null
            val grid = Array(lines.size) { rowIdx ->
                lines[rowIdx].mapIndexed { colIdx, char ->
                    when (char) {
                        'S' -> { start = HeightPoint(rowIdx, colIdx); 'a' }
                        'E' -> { end = HeightPoint(rowIdx, colIdx); 'z' }
                        else -> char
                    }
                }.toCharArray()
            }
            HeightMap(
                grid = grid,
                start = checkNotNull(start) { "No start found!" },
                end = checkNotNull(end) { "No end found!" }
            )
        }
    }
}

private data class HeightPoint(val rowIdx: Int, val colIdx: Int)
