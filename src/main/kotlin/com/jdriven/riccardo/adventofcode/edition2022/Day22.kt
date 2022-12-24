package com.jdriven.riccardo.adventofcode.edition2022

class Day22 {
    fun solvePart1(input: String): Int {
        val lines = input.lines()
        val board = MonkeyBoard(lines.dropLast(2))
        val instructions = lines.last().toInstructions()
        return calculatePassword(board = board, instructions = instructions)
    }

    fun solvePart2(input: String, cubeMappings: Map<Pair<MonkeyBoardTile, Facing>, Pair<MonkeyBoardTile, Facing>>): Int {
        val lines = input.lines()
        val board = MonkeyBoard(lines.dropLast(2))
        val instructions = lines.last().toInstructions()
        return calculatePassword(board = board, instructions = instructions, cubeMappings = cubeMappings)
    }

    private fun String.toInstructions() =
        Regex("""\d+|[LR]""").findAll(this).map { MonkeyBoardInstruction(it.value) }

    private fun calculatePassword(
        board: MonkeyBoard,
        instructions: Sequence<MonkeyBoardInstruction>,
        cubeMappings: Map<Pair<MonkeyBoardTile, Facing>, Pair<MonkeyBoardTile, Facing>> = emptyMap(),
    ) =
        instructions.fold(board.topLeft to Facing.Right) { (pos, dir), instruction ->
            when (instruction) {
                MonkeyBoardTurnRight -> pos to dir.turnRight()
                MonkeyBoardTurnLeft -> pos to dir.turnLeft()
                is MonkeyBoardMove -> board.move(pos, instruction.steps, dir, cubeMappings)
            }
        }.let { (endPos, endDir) -> ((endPos.row + 1) * 1000) + ((endPos.col + 1) * 4) + endDir.value }
}

data class MonkeyBoardTile(val row: Int, val col: Int)

class MonkeyBoard(val grid: Array<CharArray>) {

    val topLeft = grid.indexOfFirst { '.' in it }.let { row ->
        MonkeyBoardTile(row = row, col = grid[row].indexOfFirst { it == '.' })
    }

    private val horizontalRanges = hashMapOf<Int, IntRange>()
    private val verticalRanges = hashMapOf<Int, IntRange>()

    fun move(from: MonkeyBoardTile, steps: Int, direction: Facing, cubeMappings: Map<Pair<MonkeyBoardTile, Facing>, Pair<MonkeyBoardTile, Facing>>) =
        (1..steps).fold(from to direction) { (pos, dir), _ ->
            cubeMappings[pos to dir]?.let { (newPos, newDir) ->
                if (grid[newPos.row][newPos.col] != '#') newPos to newDir else pos to dir
            } ?: when (dir) {
                Facing.Right -> pos.moveHorizontal(delta = 1) to dir
                Facing.Down -> pos.moveVertical(delta = 1) to dir
                Facing.Left -> pos.moveHorizontal(delta = -1) to dir
                Facing.Up -> pos.moveVertical(delta = -1) to dir
            }
        }

    private fun MonkeyBoardTile.moveHorizontal(delta: Int): MonkeyBoardTile {
        val range = horizontalRange(row)
        val newCol = (col + delta).let { if (it < range.first) range.last else if (it > range.last) range.first else it }
        return if (grid[row][newCol] != '#') MonkeyBoardTile(row, newCol) else this
    }

    private fun MonkeyBoardTile.moveVertical(delta: Int): MonkeyBoardTile {
        val range = verticalRange(col)
        val newRow = (row + delta).let { if (it < range.first) range.last else if (it > range.last) range.first else it }
        return if (grid[newRow][col] != '#') MonkeyBoardTile(newRow, col) else this
    }

    private fun horizontalRange(rowIdx: Int) = horizontalRanges.computeIfAbsent(rowIdx) {
        grid[rowIdx].let { row ->
            row.indexOfFirst { it != ' ' }..(row.indexOfLast { it != ' ' }.takeIf { it >= 0 } ?: (row.size - 1))
        }
    }

    private fun verticalRange(colIdx: Int) = verticalRanges.computeIfAbsent(colIdx) {
        grid.map { if (it.size > colIdx) it[colIdx] else ' ' }.let { col ->
            (col.indexOfFirst { it != ' ' }.coerceAtLeast(0))..(col.indexOfLast { it != ' ' }.takeIf { it >= 0 } ?: (col.size - 1))
        }
    }

    companion object {
        operator fun invoke(lines: List<String>) = MonkeyBoard(
            grid = lines.map { it.toCharArray() }.toTypedArray(),
        )
    }
}

enum class Facing(val value: Int, val turnRight: () -> Facing, val turnLeft: () -> Facing) {
    Right(value = 0, turnRight = { Down }, turnLeft = { Up }),
    Down(value = 1, turnRight = { Left }, turnLeft = { Right }),
    Left(value = 2, turnRight = { Up }, turnLeft = { Down }),
    Up(value = 3, turnRight = { Right }, turnLeft = { Left });
}

sealed interface MonkeyBoardInstruction {
    companion object {
        operator fun invoke(input: String) = input.toIntOrNull()?.let(::MonkeyBoardMove)
            ?: when (input) {
                "L" -> MonkeyBoardTurnLeft
                "R" -> MonkeyBoardTurnRight
                else -> error("Unknown step: $input")
            }
    }
}
data class MonkeyBoardMove(val steps: Int): MonkeyBoardInstruction
object MonkeyBoardTurnRight: MonkeyBoardInstruction
object MonkeyBoardTurnLeft: MonkeyBoardInstruction
