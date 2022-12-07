package com.jdriven.riccardo.adventofcode.edition2022

class Day2 {
    fun solvePart1(input: String) =
        input.lineSequence()
            .map { Hand.create(it.first()) to Hand.create(it.last()) }
            .map { (opponent, me) -> playHand(opponent = opponent, me = me) }
            .sum()

    fun solvePart2(input: String): Int =
        input.lineSequence()
            .map { Hand.create(it.first()).let { opponent -> opponent to Hand.createByStrategy(opponent = opponent, input = it.last()) }  }
            .map { (opponent, me) -> playHand(opponent = opponent, me = me) }
            .sum()

    private fun playHand(opponent: Hand, me: Hand) = when (me.compareTo(opponent)) {
        1  -> 6 // Win
        0  -> 3 // Draw
        -1 -> 0 // Loss
        else -> error("WTF")
    } + me.points
}

sealed interface Hand: Comparable<Hand> {
    val points: Int

    val handItBeats get() = listOf(Rock, Paper, Scissors).maxBy { this.compareTo(it) }
    val handThatBeatsIt get() = listOf(Rock, Paper, Scissors).minBy { this.compareTo(it) }

    object Rock: Hand {
        override val points = 1
        override fun compareTo(other: Hand): Int = if (this == other) 0 else if (other == Scissors) 1 else -1
    }

    object Paper: Hand {
        override val points = 2
        override fun compareTo(other: Hand): Int = if (this == other) 0 else if (other == Rock) 1 else -1
    }

    object Scissors: Hand {
        override val points = 3
        override fun compareTo(other: Hand): Int = if (this == other) 0 else if (other == Paper) 1 else -1
    }

    companion object {
        fun create(input: Char) = when (input) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> error("Unknown input '$input'")
        }

        fun createByStrategy(opponent: Hand, input: Char) = when (input) {
            'X' -> opponent.handItBeats     // Lose
            'Y' -> opponent                 // Draw
            'Z' -> opponent.handThatBeatsIt // Win
            else -> error("Unknown input '$input'")
        }

    }
}
