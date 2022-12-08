package com.jdriven.riccardo.adventofcode.edition2022

private typealias Tree = Pair<Int, Int>

class Day8 {
    fun solvePart1(input: String): Int {
        val treeGrid = TreeGrid(input)
        treeGrid.print()

        val visibleTrees = mutableSetOf<Tree>()

        treeGrid.mapRows { row, trees -> visibleTrees.addAll(trees.findVisibleTrees().map { it to row }) }
        treeGrid.mapColumns { col, trees -> visibleTrees.addAll(trees.findVisibleTrees().map { col to it }) }

        println("Visible trees: $visibleTrees")
        return visibleTrees.size
    }

    private fun IntArray.findVisibleTrees() =
        foldIndexed(-1 to mutableListOf<Int>()) { idx, (max, visible), tree -> accumulateTree(tree, max, visible, idx) }.second +
        foldRightIndexed(-1 to mutableListOf<Int>()) { idx, tree, (max, visible) -> accumulateTree(tree, max, visible, idx) }.second

    private fun accumulateTree(tree: Int, max: Int, visible: MutableList<Int>, idx: Int): Pair<Int, MutableList<Int>> {
        if (tree > max) visible.add(idx)
        return max.coerceAtLeast(tree) to visible
    }

    fun solvePart2(input: String): Int {
        var max = -1
        val treeGrid = TreeGrid(input)
        treeGrid.forEachTree { row, col -> max = max.coerceAtLeast(treeGrid.scenicScore(row, col)) }
        return max
    }
}

private class TreeGrid private constructor(private val grid: Array<IntArray>) {

    val rowSize = grid.size
    val rowIndices = grid.indices
    val columnSize = grid[0].size
    val columnIndices = grid[0].indices

    fun get(row: Int, col: Int) = grid[row][col]

    fun <T> mapRows(rowMapper: (Int, IntArray) -> T): List<T> = grid.mapIndexed(rowMapper)
    fun <T> mapColumns(columnMapper: (Int, IntArray) -> T): List<T> = columnIndices.map { col ->
        rowIndices
            .map { row -> get(row, col) }
            .toIntArray()
            .let { columnMapper(col, it) }
    }

    fun forEachTree(action: (Int, Int) -> Unit) =
        rowIndices.forEach { row -> columnIndices.forEach { col -> action(row, col) } }

    fun scenicScore(row: Int, col: Int): Int {
        val height = get(row, col)

        return ((row - 1) downTo 0).map { get(it, col) }.countVisible(height) *
                ((row + 1) until rowSize).map { get(it, col) }.countVisible(height) *
                ((col - 1) downTo 0).map { get(row, it) }.countVisible(height) *
                ((col + 1) until columnSize).map { get(row, it) }.countVisible(height)
    }

    private fun List<Int>.countVisible(height: Int) = (indexOfFirst { it >= height } + 1).takeIf { it > 0 } ?: size

    fun print() {
        rowIndices.forEach { y ->
            columnIndices.forEach { x ->
                print(get(x, y))
            }
            println()
        }
    }

    companion object {
        operator fun invoke(input: String) = input.lines()
            .let { lines -> Array(lines.size) { idx -> lines[idx].map { it.digitToInt() }.toIntArray() } }
            .let { TreeGrid(it) }
    }
}
