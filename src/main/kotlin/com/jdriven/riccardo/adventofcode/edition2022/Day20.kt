package com.jdriven.riccardo.adventofcode.edition2022

import java.util.*

class Day20 {
    fun solvePart1(input: String): Long {
        val (items, zero) = input.parse()
        mix(items)
        return zero.coordinates
    }

    fun solvePart2(input: String): Long {
        val (items, zero) = input.parse(decryptionKey = 811589153)
        repeat(10) { mix(items) }
        return zero.coordinates
    }

    private fun String.parse(decryptionKey: Long = 1): Pair<List<FileNr>, FileNr> {
        val items = LinkedList<FileNr>()
        var zero: FileNr? = null

        lines()
            .map { FileNr(value = it.toLong() * decryptionKey) }
            .onEach { items.add(it) }
            .onEach { if (it.value == 0L) zero = it }
            .zipWithNext()
            .forEach { (f1, f2) -> f1.attach(f2) }
        items.last.attach(items.first)

        return items to zero!!
    }

    private fun mix(items: List<FileNr>) {
        val queue = LinkedList(items)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            next.move(items.size)
        }
    }
}

private data class FileNr(val value: Long) {
    lateinit var previous: FileNr
    lateinit var next: FileNr

    val coordinates: Long get() {
        val item1000 = scrollForward(amount = 1000)
        val item2000 = item1000.scrollForward(amount = 1000)
        val item3000 = item2000.scrollForward(amount = 1000)
        return item1000.value + item2000.value + item3000.value
    }

    fun move(size: Int) {
        val moveAmount = value.mod(size - 1)
        if (moveAmount == 0) return

        val insertPosition = scrollForward(moveAmount)
        previous.attach(next)
        this.attach(insertPosition.next)
        insertPosition.attach(this)
    }

    fun attach(other: FileNr) {
        next = other
        other.previous = this
    }

    fun scrollForward(amount: Int) = (1..amount).fold(this) { n, _ -> n.next }
}
