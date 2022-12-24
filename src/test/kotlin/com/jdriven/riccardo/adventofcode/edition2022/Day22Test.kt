package com.jdriven.riccardo.adventofcode.edition2022

import com.jdriven.riccardo.adventofcode.Utils
import com.jdriven.riccardo.adventofcode.edition2022.Facing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day22Test {

    private val example = Utils.readFile("edition2022/day22-example", trimStart = false)
    private val input = Utils.readFile("edition2022/day22-input", trimStart = false)

    @Test
    fun shouldSolveExamplePart1() {
        val result = Day22().solvePart1(example)
        assertEquals(6032, result)
    }

    @Test
    fun shouldSolveInputPart1() {
        val result = Day22().solvePart1(input)
        println(result)
        assertEquals(181128, result)
    }

    @Test
    fun shouldSolveExamplePart2() {
        val result = Day22().solvePart2(example, exampleCubeMappings)
        assertEquals(5031, result)
    }

    @Test
    fun shouldSolveInputPart2() {
        val result = Day22().solvePart2(input, inputCubeMappings)
        println(result)
        assertEquals(52311, result)
    }

    /**
     *          1111
     *          1111
     *          1111
     *          1111
     *  222233334444
     *  222233334444
     *  222233334444
     *  222233334444
     *          55556666
     *          55556666
     *          55556666
     *          55556666
     */
    private val exampleCubeMappings = 4.let { cube ->
        sequenceOf(
            // 1 up -> 2 down
            (0 until cube).map { col -> (MonkeyBoardTile(row = 0, col = (2 * cube) + col) to Up) to (MonkeyBoardTile(row = cube, col = cube - 1 - col) to Down) },
            // 1 left -> 3 down
            (0 until cube).map { row -> (MonkeyBoardTile(row = row, col = 2 * cube) to Left) to (MonkeyBoardTile(row = cube, col = row + cube) to Down) },
            // 1 right -> 6 left
            (0 until cube).map { row -> (MonkeyBoardTile(row = row, col = (3 * cube) - 1) to Right) to (MonkeyBoardTile(row = (3 * cube) - 1 - row, col = (4 * cube) - 1) to Left) },

            // 2 up -> 1 down
            (0 until cube).map { col -> (MonkeyBoardTile(row = cube, col = col) to Up) to (MonkeyBoardTile(row = 0, col = (3 * cube) - 1 - col) to Down) },
            // 2 down -> 5 up
            (0 until cube).map { col -> (MonkeyBoardTile(row = (2 * cube) - 1, col = col) to Down) to (MonkeyBoardTile(row = (3 * cube) - 1, col = (3 * cube) - 1 - col) to Up) },
            // 2 left -> 6 up
            (0 until cube).map { row -> (MonkeyBoardTile(row = cube + row, col = 0) to Left) to (MonkeyBoardTile(row = (3 * cube) - 1, col = (4 * cube) - 1 - row) to Up) },

            // 3 up -> 1 right
            (0 until cube).map { col -> (MonkeyBoardTile(row = cube, col = cube + col) to Up) to (MonkeyBoardTile(row = col, col = 2 * cube) to Right) },
            // 3 down -> 5 right
            (0 until cube).map { col -> (MonkeyBoardTile(row = (2 * cube) - 1, col = cube + col) to Down) to (MonkeyBoardTile(row = (3 * cube) - 1 - col, col = 2 * cube) to Right) },

            // 4 right -> 6 down
            (0 until cube).map { row -> (MonkeyBoardTile(row = cube + row, col = (3 * cube) - 1) to Right) to (MonkeyBoardTile(row = 2 * cube, col = (4 * cube) - 1 - row) to Down) },

            // 5 down -> 2 up
            (0 until cube).map { col -> (MonkeyBoardTile(row = (3 * cube) - 1, col = (2 * cube) + col) to Down) to (MonkeyBoardTile(row = (2 * cube) - 1, col = cube - col - 1) to Up) },
            // 5 left -> 3 up
            (0 until cube).map { row -> (MonkeyBoardTile(row = (2 * cube) + row, col = 2 * cube) to Left) to (MonkeyBoardTile(row = (2 * cube) - 1, col = (2 * cube) - 1 - row) to Up) },

            // 6 up -> 4 left
            (0 until cube).map { col -> (MonkeyBoardTile(row = 2 * cube, col = (3 * cube) + col) to Up) to (MonkeyBoardTile(row = (2 * cube) - 1 - col, col = (3 * cube) - 1) to Left) },
            // 6 down -> 2 right
            (0 until cube).map { col -> (MonkeyBoardTile(row = (3 * cube) - 1, col = (3 * cube) + col) to Down) to (MonkeyBoardTile(row = (2 * cube) - 1 - col, col = 0) to Right) },
            // 6 right -> 1 left
            (0 until cube).map { row -> (MonkeyBoardTile(row = (3 * cube) + row, col = (4 * cube) - 1) to Right) to (MonkeyBoardTile(row = cube - row, col = (3 * cube) - 1) to Left) },
        ).flatten().toMap()
    }

    /**
     *      11112222
     *      11112222
     *      11112222
     *      11112222
     *      3333
     *      3333
     *      3333
     *      3333
     *  55554444
     *  55554444
     *  55554444
     *  55554444
     *  6666
     *  6666
     *  6666
     *  6666
     */
    private val inputCubeMappings: Map<Pair<MonkeyBoardTile, Facing>, Pair<MonkeyBoardTile, Facing>> = 50.let { cube ->
        sequenceOf(
            // 1 up -> 6 right
            (0 until cube).map { col -> (MonkeyBoardTile(row = 0, col = cube + col) to Up) to (MonkeyBoardTile(row = (3 * cube) + col, col = 0) to Right) },
            // 1 left -> 5 right
            (0 until cube).map { row -> (MonkeyBoardTile(row = row, col = cube) to Left) to (MonkeyBoardTile(row = (3 * cube) - 1 - row, col = 0) to Right) },

            // 2 up -> 6 up
            (0 until cube).map { col -> (MonkeyBoardTile(row = 0, col = (2 * cube) + col) to Up) to (MonkeyBoardTile(row = (4 * cube) - 1, col = col) to Up) },
            // 2 down -> 3 left
            (0 until cube).map { col -> (MonkeyBoardTile(row = cube - 1, col = (2 * cube) + col) to Down) to (MonkeyBoardTile(row = cube + col, col = (2 * cube) - 1) to Left) },
            // 2 right -> 4 left
            (0 until cube).map { row -> (MonkeyBoardTile(row = row, col = (3 * cube) - 1) to Right) to (MonkeyBoardTile(row = (3 * cube) - 1 - row, col = (2 * cube) - 1) to Left) },

            // 3 left -> 5 down
            (0 until cube).map { row -> (MonkeyBoardTile(row = cube + row, col = cube) to Left) to (MonkeyBoardTile(row = 2 * cube, col = row) to Down) },
            // 3 right -> 2 up
            (0 until cube).map { row -> (MonkeyBoardTile(row = cube + row, col = (2 * cube) - 1) to Right) to (MonkeyBoardTile(row = cube - 1, col = (2 * cube) + row) to Up) },

            // 4 down -> 6 left
            (0 until cube).map { col -> (MonkeyBoardTile(row = (3 * cube) - 1, col = cube + col) to Down) to (MonkeyBoardTile(row = (3 * cube) + col, col = cube - 1) to Left) },
            // 4 right -> 2 left
            (0 until cube).map { row -> (MonkeyBoardTile(row = (2 * cube) + row, col = (2 * cube) - 1) to Right) to (MonkeyBoardTile(row = cube - 1 - row, col = (3 * cube) - 1) to Left) },

            // 5 up -> 3 right
            (0 until cube).map { col -> (MonkeyBoardTile(row = 2 * cube, col = col) to Up) to (MonkeyBoardTile(row = cube + col, col = cube) to Right) },
            // 5 left -> 1 right
            (0 until cube).map { row -> (MonkeyBoardTile(row = (2 * cube) + row, col = 0) to Left) to (MonkeyBoardTile(row = cube - 1 - row, col = cube) to Right) },

            // 6 down -> 2 down
            (0 until cube).map { col -> (MonkeyBoardTile(row = (4 * cube) - 1, col = col) to Down) to (MonkeyBoardTile(row = 0, col = (2 * cube) + col) to Down) },
            // 6 left -> 1 down
            (0 until cube).map { row -> (MonkeyBoardTile(row = (3 * cube) + row, col = 0) to Left) to (MonkeyBoardTile(row = 0, col = cube + row) to Down) },
            // 6 right -> 4 up
            (0 until cube).map { row -> (MonkeyBoardTile(row = (3 * cube) + row, col = cube - 1) to Right) to (MonkeyBoardTile(row = (3 * cube) - 1, col = cube + row) to Up) },
        ).flatten().toMap()
    }
}
