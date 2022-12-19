package com.jdriven.riccardo.adventofcode.edition2022

class Day19 {
    fun solvePart1(input: String): Int = input.lineSequence()
        .map { Blueprint(it) }
        .map { blueprint -> blueprint.id to findMaxGeodes(blueprint = blueprint, minutes = 24) }
        .sumOf { (blueprintId, geodes) -> blueprintId * geodes }

    fun solvePart2(input: String): Int = input.lineSequence()
        .take(3)
        .map { Blueprint(it) }
        .map { blueprint -> findMaxGeodes(blueprint = blueprint, minutes = 32) }
        .reduce(Int::times)

    private fun findMaxGeodes(
        blueprint: Blueprint,
        minutes: Int,
        state: BuildState = BuildState(),
        previousState: BuildState? = null,
        buildCache: BuildCache = BuildCache(),
    ): Int = when {
        buildCache.contains(state) -> buildCache.get(state)
        state.potentialGeodes(minutes - state.time) < buildCache.maxGeodes -> 0
        state.time == minutes -> state.geode.also { buildCache.maxGeodes = it }
        else -> state.nextStates(blueprint = blueprint, previousState = previousState, timeLeft = minutes - state.time)
            .maxOfOrNull { findMaxGeodes(blueprint = blueprint, minutes = minutes, state = it, previousState = state, buildCache = buildCache) }
            ?.also { buildCache[state] = it }
            ?: 0
    }
}

private data class Blueprint(
    val id: Int,
    val oreRobotCost: RobotCost,
    val clayRobotCost: RobotCost,
    val obsidianRobotCost: RobotCost,
    val geodeRobotCost: RobotCost,
) {
    fun canBuildOreRobot(state: BuildState) = state.ore >= oreRobotCost.ore
    fun canBuildClayRobot(state: BuildState) = state.ore >= clayRobotCost.ore
    fun canBuildObsidianRobot(state: BuildState) = state.ore >= obsidianRobotCost.ore && state.clay >= obsidianRobotCost.clay
    fun canBuildGeodeRobot(state: BuildState) = state.ore >= geodeRobotCost.ore && state.obsidian >= geodeRobotCost.obsidian

    companion object {
        operator fun invoke(input: String) =
            Regex("""Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.""")
                .matchEntire(input)
                ?.destructured
                ?.let { (id, oreCost, clayCost, obsidianOreCost, obsidianClayCost, geodeOreCost, geodeObsidianCost) ->
                    Blueprint(
                        id = id.toInt(),
                        oreRobotCost = RobotCost(ore = oreCost.toInt()),
                        clayRobotCost = RobotCost(ore = clayCost.toInt()),
                        obsidianRobotCost = RobotCost(ore = obsidianOreCost.toInt(), clay = obsidianClayCost.toInt()),
                        geodeRobotCost = RobotCost(ore = geodeOreCost.toInt(), obsidian = geodeObsidianCost.toInt()),
                    )
                }
                ?: error("Invalid blueprint: $input")
    }
}

private data class RobotCost(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
)

private data class BuildState(
    val time: Int = 0,

    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,

    val oreRobot: Int = 1,
    val clayRobot: Int = 0,
    val obsidianRobot: Int = 0,
    val geodeRobot: Int = 0,
) {
    val robotCount get() = oreRobot + clayRobot + obsidianRobot + geodeRobot

    fun potentialGeodes(timeLeft: Int) = geode + (0..timeLeft).sumOf { it * (timeLeft - it + geodeRobot) }

    fun nextStates(blueprint: Blueprint, previousState: BuildState?, timeLeft: Int): List<BuildState> {
        val skippedRobotBuild = previousState?.let { robotCount == it.robotCount } ?: false // Don't build robots that could have been built in the previous state
        return listOfNotNull(
            if (timeLeft > 1 && blueprint.canBuildGeodeRobot(this) && (!skippedRobotBuild || !blueprint.canBuildGeodeRobot(previousState!!))) next(blueprint, geodeRobotBuilt = 1) else null,
            if (timeLeft > 2 && blueprint.canBuildObsidianRobot(this) && (!skippedRobotBuild || !blueprint.canBuildObsidianRobot(previousState!!))) next(blueprint, obsidianRobotBuilt = 1) else null,
            if (timeLeft > 3 && blueprint.canBuildClayRobot(this) && (!skippedRobotBuild || !blueprint.canBuildClayRobot(previousState!!))) next(blueprint, clayRobotBuilt = 1) else null,
            if (timeLeft > 4 && blueprint.canBuildOreRobot(this) && (!skippedRobotBuild || !blueprint.canBuildOreRobot(previousState!!))) next(blueprint, oreRobotBuilt = 1) else null,
            next(blueprint),
        )
    }

    private fun next(
        blueprint: Blueprint,
        oreRobotBuilt: Int = 0,
        clayRobotBuilt: Int = 0,
        obsidianRobotBuilt: Int = 0,
        geodeRobotBuilt: Int = 0,
    ) = copy(
        time = time + 1,
        ore = ore + oreRobot - (oreRobotBuilt * blueprint.oreRobotCost.ore) - (clayRobotBuilt * blueprint.clayRobotCost.ore) - (obsidianRobotBuilt * blueprint.obsidianRobotCost.ore) - (geodeRobotBuilt * blueprint.geodeRobotCost.ore),
        clay = clay + clayRobot - (obsidianRobotBuilt * blueprint.obsidianRobotCost.clay),
        obsidian = obsidian + obsidianRobot - (geodeRobotBuilt * blueprint.geodeRobotCost.obsidian),
        geode = geode + geodeRobot,
        oreRobot = oreRobot + oreRobotBuilt,
        clayRobot = clayRobot + clayRobotBuilt,
        obsidianRobot = obsidianRobot + obsidianRobotBuilt,
        geodeRobot = geodeRobot + geodeRobotBuilt,
    )
}

private class BuildCache(
    private val cache: MutableMap<BuildState, Int> = hashMapOf(),
) {
    var maxGeodes: Int = 0
        set(value) {
            field = maxGeodes.coerceAtLeast(value)
        }

    fun get(state: BuildState) = cache.getValue(state)
    fun contains(state: BuildState) = cache.containsKey(state)
    operator fun set(state: BuildState, geodes: Int) {
        cache[state] = geodes
    }
}
