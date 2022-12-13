package com.jdriven.riccardo.adventofcode.edition2022

import kotlin.math.sign

class Day13 {
    fun solvePart1(input: String): Int = input.lines()
        .chunked(3)
        .map { parse(it[0]) to parse(it[1]) }
        .mapIndexedNotNull { idx, (left, right) -> (idx + 1).takeIf { compare(left, right) < 0 } }
        .sum()

    fun solvePart2(input: String): Int = input.lineSequence()
        .filter { it.isNotBlank() }
        .map { parse(it) }
        .plus(divider1)
        .plus(divider2)
        .sortedWith(::compare)
        .let { (it.indexOf(divider1) + 1) * (it.indexOf(divider2) + 1) }

    private fun compare(left: Element, right: Element): Int = when {
        left is ValueElement && right is ValueElement -> left.value.compareTo(right.value).sign
        left is ListElement && right is ValueElement -> compare(left, ListElement(listOf(right)))
        left is ValueElement && right is ListElement -> compare(ListElement(listOf(left)), right)
        else -> {
            left as ListElement
            right as ListElement
            left.elements.asSequence().zip(right.elements.asSequence())
                .map { (l, r) -> compare(l, r) }
                .firstOrNull { it != 0 }
                ?: (left.elements.size - right.elements.size).sign
        }
    }

    private fun parse(input: String): Element {
        check(input.startsWith('[') && input.endsWith(']'))
        var remaining = input.slice(1 until input.lastIndex)
        val elements = mutableListOf<Element>()

        while (remaining.isNotBlank()) {
            if (remaining.startsWith(',')) remaining = remaining.drop(1)
            val (nextElement, charsParsed) = parseNext(remaining)
            nextElement?.let { elements.add(it) }
            remaining = remaining.drop(charsParsed)
        }

        return ListElement(elements)
    }

    private fun parseNext(input: String): Pair<Element?, Int> {
        return when {
            input.isEmpty() -> null to 0
            input.startsWith('[') -> input.take(closingBracketIndex(input) + 1).let { list -> parse(list) to list.length }
            else -> input.takeWhile { it.isDigit() }.let { digit -> ValueElement(digit.toInt()) to digit.length }
        }
    }

    private fun closingBracketIndex(input: String): Int {
        var bracketState = 0
        input.forEachIndexed { index, c ->
            when (c) {
                '[' -> bracketState++
                ']' -> bracketState--
            }
            if (bracketState == 0) {
                return index
            }
        }
        return -1
    }
}

private sealed interface Element
private data class ListElement(val elements: List<Element>): Element
private data class ValueElement(val value: Int): Element

private val divider1 = ListElement(listOf(ListElement(listOf(ValueElement(2)))))
private val divider2 = ListElement(listOf(ListElement(listOf(ValueElement(6)))))
