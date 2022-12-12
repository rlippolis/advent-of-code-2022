package com.jdriven.riccardo.adventofcode.edition2022

import java.util.*

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
        mutableListOf<T>().apply { this@HeightMap.forEach { add(transform(it, get(it))) } }

    fun findShortestRoute(startingPoint: HeightPoint = start): List<HeightPoint>? {
        val nodes = mutableMapOf<HeightPoint, ShortestPathNode>()
        forEach { point ->
            val node = nodes.getOrCreate(point)
            getTraversableNeighbours(point).forEach { neighbour ->
                val neighbourNode = nodes.getOrCreate(neighbour)
                node.addDestination(neighbourNode, neighbourNode.value)
            }
        }

        val startNode = nodes.getValue(startingPoint)
        calculateShortestDistanceFromSource(startNode)

        val shortestPath = LinkedList<ShortestPathNode>()

        var node: ShortestPathNode? = nodes.getValue(end)
        do {
            shortestPath.push(node!!)
            node = node.previousNode
        } while (node != null && node.distance != Int.MAX_VALUE)

        return shortestPath.map { HeightPoint(rowIdx = it.rowIdx, colIdx = it.colIdx) }
            .takeIf { startingPoint in it }
    }

    private inline fun forEach(action: (HeightPoint) -> Unit) =
        rowIndices.forEach { rowIdx -> columnIndices.forEach { colIdx -> action(HeightPoint(rowIdx = rowIdx, colIdx = colIdx)) } }

    private fun get(point: HeightPoint) = grid[point.rowIdx][point.colIdx]

    private fun getTraversableNeighbours(point: HeightPoint): List<HeightPoint> {
        val currentHeight = get(point)
        val (rowIdx, colIdx) = point
        return listOfNotNull(
            HeightPoint(rowIdx = rowIdx - 1, colIdx = colIdx).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx + 1, colIdx = colIdx).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx, colIdx = colIdx - 1).takeIf { canTravelTo(it, currentHeight) },
            HeightPoint(rowIdx = rowIdx, colIdx = colIdx + 1).takeIf { canTravelTo(it, currentHeight) },
        )
    }

    private fun canTravelTo(point: HeightPoint, fromHeight: Char): Boolean =
        point.rowIdx in rowIndices && point.colIdx in columnIndices && get(point).code <= fromHeight.code + 1

    private fun MutableMap<HeightPoint, ShortestPathNode>.getOrCreate(point: HeightPoint) =
        computeIfAbsent(point) { (rowIdx, colIdx) -> ShortestPathNode(rowIdx = rowIdx, colIdx = colIdx, value = 1) }

    private fun calculateShortestDistanceFromSource(source: ShortestPathNode) {
        source.distance = 0

        val settledNodes = hashSetOf<ShortestPathNode>()
        val unsettledNodes = hashSetOf<ShortestPathNode>()

        unsettledNodes.add(source)

        while (unsettledNodes.isNotEmpty()) {
            val currentNode = unsettledNodes.minByOrNull { it.distance }!!
            unsettledNodes.remove(currentNode)
            currentNode.adjacentNodes.forEach { (adjacentNode, edgeWeight) ->
                if (adjacentNode !in settledNodes) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode)
                    unsettledNodes.add(adjacentNode)
                }
            }
            settledNodes.add(currentNode)
        }
    }

    private fun calculateMinimumDistance(evaluationNode: ShortestPathNode, edgeWeight: Int, sourceNode: ShortestPathNode) {
        val sourceDistance = sourceNode.distance
        if (sourceDistance + edgeWeight < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + edgeWeight
            evaluationNode.previousNode = sourceNode
        }
    }

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

private data class ShortestPathNode(
    val rowIdx: Int,
    val colIdx: Int,
    val value: Int,
) {
    var previousNode: ShortestPathNode? = null
    var distance = Int.MAX_VALUE
    val adjacentNodes = hashMapOf<ShortestPathNode, Int>()

    fun addDestination(destination: ShortestPathNode, distance: Int) {
        adjacentNodes[destination] = distance
    }
}
