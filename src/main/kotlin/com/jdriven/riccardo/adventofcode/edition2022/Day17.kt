package com.jdriven.riccardo.adventofcode.edition2022

class Day17 {
    fun solvePart1(input: String): Long = input.iterateRocks(2022)

    fun solvePart2(input: String): Long = input.iterateRocks(1_000_000_000_000)

    private fun String.iterateRocks(total: Long): Long {
        val tower = Tower()
        val jetStream = jetStream()
        val rocks = rocks()
        val caveStates = hashMapOf<CaveStateKey, CaveStateValue>()

        var count = 1L
        while (count < total) {
            dropRock(rocks = rocks, tower = tower, jetStream = jetStream)

            val state = CaveStateKey(towerTopView = tower.topView, jetIdx = jetStream.idx, rockIdx = rocks.idx)
            if (caveStates.containsKey(state)) {
                val (loopStart, previousTowerSize) = caveStates.getValue(state)
                val loopSize = count - loopStart
                val nrOfRepeats = (total - count) / loopSize
                val skippedSize = nrOfRepeats * (tower.size - previousTowerSize)

                repeat((total - count - nrOfRepeats * loopSize).toInt()) {
                    dropRock(rocks = rocks, tower = tower, jetStream = jetStream)
                }
                return tower.size + skippedSize
            }

            caveStates[state] = CaveStateValue(iteration = count, towerSize = tower.size)
            count++
        }

        return tower.size.toLong()
    }

    private fun dropRock(tower: Tower, rocks: InfiniteCollection<List<RockPoint>>, jetStream: InfiniteCollection<Int>) {
        var rockPos = rocks.next().moveY(tower.offset)
        while (true) {
            rockPos = rockPos.moveX(jetStream.next()).takeIf { tower.isValidRock(it) } ?: rockPos
            rockPos = rockPos.moveY(-1).takeIf { tower.isValidRock(it) } ?: break
        }
        tower.add(rockPos)
    }

    private fun String.jetStream() = InfiniteCollection(map { c ->
        when (c) {
            '>' -> 1
            '<' -> -1
            else -> error("Unknown char: $c")
        }
    })

    private fun rocks() = InfiniteCollection(listOf(
        """
            ####
        """.toRock(),

        """
            .#.
            ###
            .#.
        """.toRock(),

        """
            ..#
            ..#
            ###
        """.toRock(),

        """
            #
            #
            #
            #
        """.toRock(),

        """
            ##
            ##
        """.toRock(),
    ))

    private fun String.toRock(): List<RockPoint> = trimIndent()
        .lines()
        .reversed()
        .flatMapIndexed { y: Int, line: String ->
            line.mapIndexedNotNull { x, c -> c.takeIf { it == '#' }?.let { RockPoint(x = x + 2, y = y) } }
        }

    private fun List<RockPoint>.moveX(offset: Int) = map { it.moveX(offset) }
    private fun List<RockPoint>.moveY(offset: Int) = map { it.moveY(offset) }
}

private data class InfiniteCollection<T>(private val items: List<T>) {
    var idx = 0
        private set
    fun next() = items[idx].also { advance(1) }
    fun advance(amount: Long) {
        idx = ((idx + amount) % items.size).toInt()
    }
}

private data class RockPoint(val x: Int, val y: Int) {
    fun moveX(offset: Int) = copy(x = x + offset)
    fun moveY(offset: Int) = copy(y = y + offset)
}

private class Tower {
    var rows = mutableListOf<MutableSet<Int>>()
    private val horizontalRange = 0..6

    val size get() = rows.size
    val offset get() = rows.size + 3

    fun isValidRock(points: Collection<RockPoint>) = points.all { point ->
        point.x in horizontalRange &&
                point.y >= 0 &&
                !(rows.getOrNull(point.y)?.contains(point.x) ?: false)
    }

    fun add(points: Collection<RockPoint>) = points.forEach { point ->
        while (rows.size < point.y + 1) rows.add(hashSetOf())
        rows[point.y].add(point.x)
    }

    val topView: List<Int> get() = horizontalRange.map { col -> topOffset(col) }.let { offsets ->
        val min = offsets.min()
        offsets.map { it - min }
    }

    private fun topOffset(col: Int): Int = rows.indexOfLast { col in it } + 1
}

private data class CaveStateKey(val towerTopView: List<Int>, val jetIdx: Int, val rockIdx: Int)
private data class CaveStateValue(val iteration: Long, val towerSize: Int)
