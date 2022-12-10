package day09

import java.lang.RuntimeException

class Day09 {

    companion object {

        fun part1(input: List<String>): Int {
            return walkPath(input, 2)
        }

        fun part2(input: List<String>): Int {
            return walkPath(input, 10)
        }

        private fun walkPath(input: List<String>, amountOfKnots: Int): Int {
            val movements = parseInput(input)
            val visitedLocations = mutableSetOf(Point()) // And add start position

            val knots = mutableListOf<Point>().apply {
                repeat(amountOfKnots) {
                    add(Point())
                }
            }

            movements.forEach { move ->
                repeat(move.amount) { _ ->
                    val head = knots.first()
                    head.updateLocation(move.direction)

                    knots.windowed(2).forEach {
                        val (first, second) = it

                        when {
                            first isAboveRight second -> second.updateLocation(Direction.UP_RIGHT)
                            first isAboveLeft second -> second.updateLocation(Direction.UP_LEFT)
                            first isBelowRight second -> second.updateLocation(Direction.DOWN_RIGHT)
                            first isBelowLeft second -> second.updateLocation(Direction.DOWN_LEFT)
                            first isAbove second -> second.updateLocation(Direction.UP)
                            first isBelow second -> second.updateLocation(Direction.DOWN)
                            first isLeftOf second -> second.updateLocation(Direction.LEFT)
                            first isRightOf second -> second.updateLocation(Direction.RIGHT)
                        }
                    }

                    visitedLocations.addCopy(knots.last())
                }
            }

            //printGrid(visitedLocations, knots)

            return visitedLocations.size
        }

//        private fun printGrid(visitedLocations: Set<Point>, knots: MutableList<Point>) {
//            val size = 4
//            val lines = mutableListOf<String>()
//            for (y in 0..size) {
//                var line = ""
//                for (x in 0..size + 1) {
//                    line += if (x == head.x && y == head.y) {
//                        "H"
//                    } else if (x == tail.x && y == tail.y) {
//                        "T"
//                    } else if (visitedLocations.contains(Point(x, y))) {
//                        "#"
//                    } else {
//                        "."
//                    }
//                }
//                lines.add(line)
//            }
//
//            lines.asReversed().forEach(::println)
//        }

        data class Point(var x: Int = 0, var y: Int = 0) {
            fun updateLocation(direction: Direction) {
                x += direction.x
                y += direction.y
            }

            infix fun isAbove(other: Point) = this.y > other.y && (this.y - other.y) > 1
            infix fun isBelow(other: Point) = this.y < other.y && (other.y - this.y) > 1
            infix fun isLeftOf(other: Point) = this.x < other.x && (other.x - this.x) > 1
            infix fun isRightOf(other: Point) = this.x > other.x && (this.x - other.x) > 1

            infix fun isAboveRight(other: Point): Boolean {
                if (this.y > other.y && this.x > other.x) {
                    return (this.y - other.y) > 1 || (this.x - other.x) > 1
                }
                return false
            }

            infix fun isAboveLeft(other: Point): Boolean {
                if (this.y > other.y && this.x < other.x) {
                    return (this.y - other.y) > 1 || (other.x - this.x) > 1
                }
                return false
            }

            infix fun isBelowRight(other: Point): Boolean {
                if (this.y < other.y && this.x > other.x) {
                    return (other.y - this.y) > 1 || (this.x - other.x) > 1
                }
                return false
            }

            infix fun isBelowLeft(other: Point): Boolean {
                if (this.y < other.y && this.x < other.x) {
                    return (other.y - this.y) > 1 || (other.x - this.x) > 1
                }
                return false
            }
        }

        enum class Direction(val x: Int, val y: Int) {
            UP(0, 1),
            RIGHT(1, 0),
            DOWN(0, -1),
            LEFT(-1, 0),
            UP_RIGHT(1, 1),
            UP_LEFT(-1, 1),
            DOWN_RIGHT(1, -1),
            DOWN_LEFT(-1, -1),
        }

        data class Movement(val direction: Direction, val amount: Int)

        private fun parseInput(input: List<String>): List<Movement> {
            return input.map {
                val (direction, amount) = it.split(" ")

                when (direction) {
                    "U" -> Movement(Direction.UP, amount.toInt())
                    "D" -> Movement(Direction.DOWN, amount.toInt())
                    "R" -> Movement(Direction.RIGHT, amount.toInt())
                    "L" -> Movement(Direction.LEFT, amount.toInt())
                    else -> throw RuntimeException("Could not map char '$direction' to Direction")
                }
            }
        }

        private fun MutableSet<Point>.addCopy(point: Point) {
            this.add(point.copy())
        }
    }
}