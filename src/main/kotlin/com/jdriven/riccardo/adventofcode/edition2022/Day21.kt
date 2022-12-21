package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.edition2022.MonkeyTreeDirection.Left
import com.jdriven.riccardo.adventofcode.edition2022.MonkeyTreeDirection.Right

class Day21 {
    fun solvePart1(input: String): Long = input.parseMonkeys().getValue("root").number

    fun solvePart2(input: String): Long {
        val monkeys = input.parseMonkeys()

        val path = monkeys.findPathFromRootToHuman()
        val root = monkeys.getValue("root") as CalcMonkey
        var (currentMonkey, number) = when (path.first()) {
            Left -> monkeys.getValue(root.left) to monkeys.getValue(root.right).number
            Right -> monkeys.getValue(root.right) to monkeys.getValue(root.left).number
        }

        path.drop(1).forEach { direction ->
            val operation = (currentMonkey as CalcMonkey).operation
            val (nextMonkey, otherValue) = when (direction) {
                Left -> monkeys.getValue((currentMonkey as CalcMonkey).left) to monkeys.getValue((currentMonkey as CalcMonkey).right).number
                Right -> monkeys.getValue((currentMonkey as CalcMonkey).right) to monkeys.getValue((currentMonkey as CalcMonkey).left).number
            }
            number = when (operation) {
                longAdd -> number - otherValue
                longSub -> when (direction) {
                    Left -> number + otherValue
                    Right -> otherValue - number
                }
                longMul -> number / otherValue
                longDiv -> when (direction) {
                    Left -> number * otherValue
                    Right -> otherValue / number
                }
                else -> error("Unknown operation!")
            }
            currentMonkey = nextMonkey
        }

        return number
    }

    private fun String.parseMonkeys(): MutableMap<String, RiddleMonkey> {
        val monkeys = mutableMapOf<String, RiddleMonkey>()
        val monkeyListener: (String) -> RiddleMonkey = monkeys::getValue

        lineSequence()
            .map { line -> line.split(": ") }
            .map { (id, operation) ->
                val operationTokens = operation.split(' ')
                when {
                    operationTokens.size == 1 ->  NumberMonkey(id = id, number = operationTokens.first().toLong())
                    operationTokens[1] == "+" -> AddMonkey(id = id, left = operationTokens[0], right = operationTokens[2], monkeyListener = monkeyListener)
                    operationTokens[1] == "-" -> SubtractMonkey(id = id, left = operationTokens[0], right = operationTokens[2], monkeyListener = monkeyListener)
                    operationTokens[1] == "*" -> MultiplyMonkey(id = id, left = operationTokens[0], right = operationTokens[2], monkeyListener = monkeyListener)
                    operationTokens[1] == "/" -> DivideMonkey(id = id, left = operationTokens[0], right = operationTokens[2], monkeyListener = monkeyListener)
                    else -> error("Unknown operation: $operation")
                }
            }.forEach { monkeys[it.id] = it }

        return monkeys
    }

    private fun Map<String, RiddleMonkey>.findPathFromRootToHuman(): List<MonkeyTreeDirection> {
        fun findPath(monkey: RiddleMonkey, path: List<MonkeyTreeDirection> = emptyList()): List<MonkeyTreeDirection>? {
            return when {
                monkey.id == "humn" -> path
                monkey is NumberMonkey -> null
                else -> {
                    monkey as CalcMonkey
                    findPath(getValue(monkey.left), path + Left)
                        ?: findPath(getValue(monkey.right), path + Right)
                }
            }
        }

        val root = getValue("root")
        return findPath(root) ?: error("No path found from root to humn")
    }

    companion object {
        val longAdd: (Long, Long) -> Long = Long::plus
        val longSub: (Long, Long) -> Long = Long::minus
        val longMul: (Long, Long) -> Long = Long::times
        val longDiv: (Long, Long) -> Long = Long::div
    }
}

private sealed class RiddleMonkey {
    abstract val id: String
    abstract val number: Long

    override fun equals(other: Any?) = this === other || id == (other as? RiddleMonkey)?.id
    override fun hashCode() = id.hashCode()
}
private sealed class CalcMonkey(override val id: String, val left: String, val right: String, val operation: (Long, Long) -> Long, val monkeyListener: (String) -> RiddleMonkey): RiddleMonkey() {
    override val number: Long by lazy { operation(monkeyListener(left).number, monkeyListener(right).number) }
}
private class NumberMonkey(override val id: String, override val number: Long): RiddleMonkey()
private class AddMonkey(id: String, left: String, right: String, monkeyListener: (String) -> RiddleMonkey): CalcMonkey(id, left, right, Day21.longAdd, monkeyListener)
private class SubtractMonkey(id: String, left: String, right: String, monkeyListener: (String) -> RiddleMonkey): CalcMonkey(id, left, right, Day21.longSub, monkeyListener)
private class MultiplyMonkey(id: String, left: String, right: String, monkeyListener: (String) -> RiddleMonkey): CalcMonkey(id, left, right, Day21.longMul, monkeyListener)
private class DivideMonkey(id: String, left: String, right: String, monkeyListener: (String) -> RiddleMonkey): CalcMonkey(id, left, right, Day21.longDiv, monkeyListener)

private enum class MonkeyTreeDirection {
    Left,
    Right,
}
