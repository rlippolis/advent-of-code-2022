package com.jdriven.riccardo.adventofcode

import java.util.*

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

object Dijkstra {
    fun <T> findShortestRoute(nodes: Collection<T>, start: T, end: T, neighbourGetter: (T) -> Collection<Pair<T, Int>>, initialWeight: Int = 0): List<T>? {
        val nodeMapping = mutableMapOf<T, ShortestPathNode<T>>()
        nodes.forEach { node ->
            val pathNode = nodeMapping.getOrCreate(node, initialWeight)
            neighbourGetter(node).forEach { (neighbour, weight) ->
                val neighbourNode = nodeMapping.getOrCreate(neighbour, weight)
                pathNode.addDestination(neighbourNode, neighbourNode.value)
            }
        }

        val startNode = nodeMapping.getValue(start)
        calculateShortestDistanceFromSource(startNode)

        val shortestPath = LinkedList<ShortestPathNode<T>>()

        var node: ShortestPathNode<T>? = nodeMapping.getValue(end)
        do {
            shortestPath.push(node!!)
            node = node.previousNode
        } while (node != null && node.distance != Int.MAX_VALUE)

        return shortestPath.map { it.graphNode }.takeIf { start in it }
    }

    private fun <T> MutableMap<T, ShortestPathNode<T>>.getOrCreate(point: T, weight: Int) =
        computeIfAbsent(point) { ShortestPathNode(graphNode = point, value = weight) }

    private fun <T> calculateShortestDistanceFromSource(source: ShortestPathNode<T>) {
        source.distance = 0

        val settledNodes = hashSetOf<ShortestPathNode<T>>()
        val unsettledNodes = hashSetOf<ShortestPathNode<T>>()

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

    private fun <T> calculateMinimumDistance(evaluationNode: ShortestPathNode<T>, edgeWeight: Int, sourceNode: ShortestPathNode<T>) {
        val sourceDistance = sourceNode.distance
        if (sourceDistance + edgeWeight < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + edgeWeight
            evaluationNode.previousNode = sourceNode
        }
    }

    data class ShortestPathNode<T>(
        val graphNode: T,
        val value: Int,
    ) {
        var previousNode: ShortestPathNode<T>? = null
        var distance = Int.MAX_VALUE
        val adjacentNodes = hashMapOf<ShortestPathNode<T>, Int>()

        fun addDestination(destination: ShortestPathNode<T>, distance: Int) {
            adjacentNodes[destination] = distance
        }
    }
}
