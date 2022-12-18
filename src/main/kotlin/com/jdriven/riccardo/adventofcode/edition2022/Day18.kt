package com.jdriven.riccardo.adventofcode.edition2022

class Day18 {
    fun solvePart1(input: String): Int = input.toCubes().let { cubes ->
        cubes.sumOf { cube -> cube.neighbours.count { it !in cubes } }
    }

    fun solvePart2(input: String): Int = input.toCubes().let { cubes ->
        val (xRange, yRange, zRange) = cubes.outsideBounds()

        val visited = hashSetOf<LavaCube>()
        val queue = ArrayDeque<LavaCube>().apply { add(LavaCube(xRange.first, yRange.first, zRange.first)) }
        var result = 0

        while (queue.isNotEmpty()) {
            val cube = queue.removeFirst()
            if (visited.add(cube)) {
                cube.neighbours
                    .filter {
                        it.x in xRange && it.y in yRange && it.z in zRange
                    }
                    .forEach {
                        if (it in cubes)
                            result++
                        else
                            queue.add(it)
                    }
            }
        }

        return result
    }

    private fun String.toCubes() = lineSequence()
        .map { it.split(',') }
        .map { (x, y, z) -> LavaCube(x = x.toInt(), y = y.toInt(), z = z.toInt()) }
        .toSet()

    private fun Set<LavaCube>.outsideBounds() = Triple(
        first =  minOf { it.x - 1 }..maxOf { it.x + 1 },
        second = minOf { it.y - 1 }..maxOf { it.y + 1 },
        third =  minOf { it.z - 1 }..maxOf { it.z + 1 },
    )
}

private data class LavaCube(val x: Int, val y: Int, val z: Int) {

    val neighbours by lazy {
        setOf(
            copy(x = x + 1),
            copy(x = x - 1),
            copy(y = y + 1),
            copy(y = y - 1),
            copy(z = z + 1),
            copy(z = z - 1),
        )
    }
}
