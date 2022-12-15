package com.jdriven.riccardo.adventofcode.edition2022

import kotlin.math.abs

class Day15 {
    fun solvePart1(input: String, position: Int): Int {
        val tunnelMap = hashMapOf<Int, MutableSet<IntRange>>()

        val sensors = input.parseSensors()
        sensors.flatMap { sensor -> sensor.ranges(position) }
            .filter { (y, _) -> y == position }.forEach { (y, range) -> tunnelMap.add(y, range) }

        return tunnelMap.getValue(position)
            .flatMap { it.toSet() }
            .distinct()
            .size
            .minus(sensors.map { it.beacon.y }.distinct().count { it == position })
    }

    fun solvePart2(input: String, maxCoordinate: Int): Long {
        val sensors = input.parseSensors()
        val pointsToScan = sensors.flatMap { sensor -> sensor.outsideRangeEdges(0..maxCoordinate) }
        val distressBeacon = pointsToScan.first { point -> sensors.none { it.isInRange(point) } }

        return (distressBeacon.x * 4000000L) + distressBeacon.y.toLong()
    }

    private fun String.parseSensors() = lineSequence()
        .mapNotNull { lineRegex.matchEntire(it)?.destructured }
        .map { (sX, sY, bX, bY) ->
            TunnelSensor(
                sensor = TunnelPoint(x = sX.toInt(), y = sY.toInt()),
                beacon = TunnelPoint(x = bX.toInt(), y = bY.toInt()),
            )
        }
        .toList()

    private fun MutableMap<Int, MutableSet<IntRange>>.add(y: Int, range: IntRange) =
        computeIfAbsent(y) { hashSetOf() }.add(range)

    private val lineRegex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
}

private data class TunnelPoint(val x: Int, val y: Int)

private data class TunnelSensor(val sensor: TunnelPoint, val beacon: TunnelPoint) {
    val range = manhattanDistance(sensor, beacon)

    fun ranges(yFilter: Int?) = range.absoluteRange()
        .filter { yDistance -> yFilter?.let { (sensor.y + yDistance) == it } ?: true }
        .map { it to abs(range - abs(it)).absoluteRange() }
        .map { (yDistance, xRange) ->
            (sensor.y + yDistance) to (xRange.first + sensor.x)..(xRange.last + sensor.x)
        }

    fun outsideRangeEdges(searchRange: IntRange) = (range + 1).absoluteRange()
        .map { it to abs((range + 1) - abs(it)).absoluteRange() }
        .map { (yDistance, xRange) ->
            (sensor.y + yDistance) to (xRange.first + sensor.x)..(xRange.last + sensor.x)
        }
        .filter { (y, _) -> y in searchRange }
        .flatMap { (y, xRange) ->
            listOfNotNull(
                xRange.first.takeIf { it in searchRange }?.let { TunnelPoint(it, y) },
                xRange.last.takeIf { it in searchRange }?.let { TunnelPoint(it, y) },
            )
        }

    fun isInRange(point: TunnelPoint) = manhattanDistance(sensor, point) <= range

    private fun Int.absoluteRange() = ((this * -1)..this)

    private fun manhattanDistance(point1: TunnelPoint, point2: TunnelPoint) =
        abs(point1.x - point2.x) + abs(point1.y - point2.y)
}
