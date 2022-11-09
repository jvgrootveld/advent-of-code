package day06

import java.io.Serializable
import java.lang.UnsupportedOperationException
import java.util.stream.Stream

class Day06 {
    companion object {

        fun part1(input: List<String>): Int {
            val grid = Grid(1000, 1000)

            input.forEach {
                ModernCommand.fromInput(it).invokeOnGrid(grid)
            }

            return grid.lightBrightness()
        }

        fun part2(input: List<String>): Int {
            val grid = Grid(1000, 1000)

            input.forEach {
                AncientNordicElvishCommand.fromInput(it).invokeOnGrid(grid)
            }

            return grid.lightBrightness()
        }

        class Grid(rowSize: Int, columnSize: Int) {
            companion object {
                const val ON = 1;
                const val OFF = 0;
            }

            private val grid = Array(rowSize) { IntArray(columnSize) }

            fun lightAction(fromPoint: Point, toPoint: Point, action: (Int) -> Int) {
                for (x in fromPoint.x..toPoint.x) {
                    for (y in fromPoint.y..toPoint.y) {
                        grid[x][y] = action(grid[x][y])
                    }
                }
            }

            fun lightBrightness(): Int = grid.sumOf(IntArray::sum)
        }
    }

    data class Point(
        val x: Int,
        val y: Int
    ) : Serializable {
        override fun toString(): String = "($x, $y)"
    }

    enum class Action {
        LIGHTS_ON,
        LIGHTS_OFF,
        TOGGLE_LIGHTS
    }

    abstract class Command(private val action: Action, val fromPoint: Point, val toPoint: Point) {

        abstract fun turnLightsOn(grid: Grid)
        abstract fun turnLightsOff(grid: Grid)
        abstract fun toggleLights(grid: Grid)

        fun invokeOnGrid(grid: Grid) {
            when (action) {
                Action.LIGHTS_ON -> turnLightsOn(grid)
                Action.LIGHTS_OFF -> turnLightsOff(grid)
                Action.TOGGLE_LIGHTS -> toggleLights(grid)
            }
        }

        companion object {
            private const val PREFIX_TURN_ON = "turn on "
            private const val PREFIX_TURN_OFF = "turn off "
            private const val PREFIX_TOGGLE = "toggle "

            /**
             * Parses command string like
             * - "turn on 0,0 through 999,999"
             * - "toggle 0,0 through 999,0"
             * - "turn off 499,499 through 500,500"
             */
            fun parseInput(input: String): Triple<Action, Point, Point> {
                var action = Action.LIGHTS_OFF
                var fromPoint = Point(0, 0)
                var toPoint = Point(0, 0)

                Stream.of(PREFIX_TURN_ON, PREFIX_TURN_OFF, PREFIX_TOGGLE)
                    .filter(input::startsWith)
                    .forEach {
                        action = when (it) {
                            PREFIX_TURN_ON -> Action.LIGHTS_ON
                            PREFIX_TURN_OFF -> Action.LIGHTS_OFF
                            PREFIX_TOGGLE -> Action.TOGGLE_LIGHTS
                            else -> throw UnsupportedOperationException("Unknown action '$it'")
                        }

                        parseInputToPoints(input.removePrefix(it)).also { points ->
                            fromPoint = points.first
                            toPoint = points.second
                        }
                    }

                return Triple(action, fromPoint, toPoint)
            }

            /**
             * Input without the action prefix.
             */
            private fun parseInputToPoints(input: String): Pair<Point, Point> {
                val (fromValue, _, toValue) = input.split(' ')

                val fromPoint = fromValue.split(',').let {
                    Point(it[0].toInt(), it[1].toInt())
                }
                val toPoint = toValue.split(',').let {
                    Point(it[0].toInt(), it[1].toInt())
                }

                return Pair(fromPoint, toPoint)
            }
        }
    }

    class ModernCommand(action: Action, fromPoint: Point, toPoint: Point) : Command(action, fromPoint, toPoint) {
        companion object {
            fun fromInput(input: String): ModernCommand {
                val (action, fromPoint, toPoint) = parseInput(input)
                return ModernCommand(action, fromPoint, toPoint)
            }
        }

        override fun turnLightsOn(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            Grid.ON
        }

        override fun turnLightsOff(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            Grid.OFF
        }

        override fun toggleLights(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            if (it == Grid.ON) Grid.OFF else Grid.ON
        }
    }

    class AncientNordicElvishCommand(action: Action, fromPoint: Point, toPoint: Point) :
        Command(action, fromPoint, toPoint) {
        companion object {
            fun fromInput(input: String): AncientNordicElvishCommand {
                val (action, fromPoint, toPoint) = parseInput(input)
                return AncientNordicElvishCommand(action, fromPoint, toPoint)
            }
        }

        override fun turnLightsOn(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            it + 1
        }

        override fun turnLightsOff(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            if (it > 0) it - 1 else 0
        }

        override fun toggleLights(grid: Grid) = grid.lightAction(fromPoint, toPoint) {
            it + 2
        }
    }
}