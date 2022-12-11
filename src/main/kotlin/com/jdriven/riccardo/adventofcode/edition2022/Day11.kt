package com.jdriven.riccardo.adventofcode.edition2022

class Day11 {
    fun solvePart1(input: String): Long {
        val monkeys = input.parseMonkeys()
        repeat(20) { monkeys.doTurn { it / 3 } }
        return monkeys.calculateMonkeyBusiness()
    }

    fun solvePart2(input: String): Long {
        val monkeys = input.parseMonkeys()
        val commonDivisor = monkeys.map { it.divisor }.reduce(Long::times)
        repeat(10000) { monkeys.doTurn { it % commonDivisor } }
        return monkeys.calculateMonkeyBusiness()
    }

    private fun List<Monkey>.doTurn(worryReducer: (Long) -> Long) {
        forEach { monkey ->
            monkey.takeTurn(worryReducer).forEach { t -> this[t.monkey].catchItem(t.item) }
        }
    }

    private fun List<Monkey>.calculateMonkeyBusiness() = map { it.nrOfInspections }
        .sortedDescending()
        .take(2)
        .reduce(Long::times)

    private fun String.parseMonkeys() = lines()
        .chunked(7)
        .map { it.parseMonkey() }

    private fun List<String>.parseMonkey() = Monkey(
        items = this[1].trim().substringAfter("Starting items: ").split(", ").mapTo(mutableListOf()) { it.toLong() },
        operation = this[2].trim().substringAfter("Operation: new = old ").split(' ').let { (op, x) ->
            val operand = x.toLongOrNull() // Shortcut: if not a number, probably 'old'
            when (op) {
                "*" -> { old -> old * (operand ?: old) }
                "+" -> { old -> old + (operand ?: old) }
                else -> error("Unknown operator: $op")
            }
        },
        divisor = this[3].trim().substringAfter("Test: divisible by ").toLong(),
        monkeyIfTestTrue = this[4].trim().substringAfter("If true: throw to monkey ").toInt(),
        monkeyIfTestFalse = this[5].trim().substringAfter("If false: throw to monkey ").toInt(),
    )
}

private class Monkey(
    private val items: MutableList<Long>,
    private val operation: (Long) -> Long,
    val divisor: Long,
    private val monkeyIfTestTrue: Int,
    private val monkeyIfTestFalse: Int,
) {
    var nrOfInspections = 0L
        private set

    fun takeTurn(worryReducer: (Long) -> Long): List<Throw> =
        items.map { throwItem(item = it, worryReducer = worryReducer) }
            .also { nrOfInspections += it.size }
            .also { items.clear() }

    fun catchItem(item: Long) = items.add(item)

    private fun throwItem(item: Long, worryReducer: (Long) -> Long) = item
        .let(operation)
        .let(worryReducer)
        .let { Throw(item = it, monkey = if (it % divisor == 0L) monkeyIfTestTrue else monkeyIfTestFalse) }
}

private data class Throw(val item: Long, val monkey: Int)
