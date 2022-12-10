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
                            first distanceAboveRightOf second > 1 -> second.updateLocation(Direction.UP_RIGHT)
                            first distanceAboveLeftOf second > 1 -> second.updateLocation(Direction.UP_LEFT)
                            first distanceBelowRightOf second > 1 -> second.updateLocation(Direction.DOWN_RIGHT)
                            first distanceBelowLeftOf second > 1 -> second.updateLocation(Direction.DOWN_LEFT)
                            first distanceAboveOf second > 1 -> second.updateLocation(Direction.UP)
                            first distanceBelowOf second > 1 -> second.updateLocation(Direction.DOWN)
                            first distanceLeftOf second > 1 -> second.updateLocation(Direction.LEFT)
                            first distanceRightOf second > 1 -> second.updateLocation(Direction.RIGHT)
                        }
                    }

                    visitedLocations.addCopy(knots.last())
                }
            }

            //printGrid(visitedLocations, knots)

            return visitedLocations.size
        }

        private fun printGrid(visitedLocations: Set<Point>, knots: MutableList<Point>) {
            val minY = minOf(visitedLocations.minOf(Point::y), knots.minOf(Point::y))
            val maxY = maxOf(visitedLocations.maxOf(Point::y), knots.maxOf(Point::y))
            val minX = minOf(visitedLocations.minOf(Point::x), knots.minOf(Point::x))
            val maxX = maxOf(visitedLocations.maxOf(Point::x), knots.maxOf(Point::x))
            val lines = mutableListOf<String>()

            for (y in minY..maxY) {
                var line = ""
                for (x in minX..maxX + 1) {

                    var knotDrawn = false
                    knots.reversed()
                        .forEachIndexed { i, knot ->
                            if (knot == Point(x, y)) {
                                knotDrawn = true
                                line += if (i == knots.lastIndex) {
                                    "H"
                                } else {
                                    i + 1
                                }
                            }
                        }

                    if (knotDrawn) {
                        continue
                    }

                    line += if (visitedLocations.contains(Point(x, y))) {
                        "#"
                    } else {
                        "."
                    }
                }
                lines.add(line)
            }

            lines.asReversed().forEach(::println)
        }

        data class Point(var x: Int = 0, var y: Int = 0) {
            fun updateLocation(direction: Direction) {
                x += direction.x
                y += direction.y
            }

            infix fun distanceAboveOf(other: Point) = if (this.y > other.y) (this.y - other.y) else 0
            infix fun distanceBelowOf(other: Point) = if (this.y < other.y) (other.y - this.y) else 0
            infix fun distanceLeftOf(other: Point) = if (this.x < other.x) (other.x - this.x) else 0
            infix fun distanceRightOf(other: Point) = if (this.x > other.x) (this.x - other.x) else 0

            private fun calcMaxDiagonalDistance(distanceA: Int, distanceB: Int): Int {
                if (distanceA > 0 && distanceB > 0) {
                    return maxOf(distanceA, distanceB)
                }

                return 0
            }


            infix fun distanceAboveRightOf(other: Point): Int {
                return calcMaxDiagonalDistance(
                    this distanceAboveOf other,
                    this distanceRightOf other
                )
            }

            infix fun distanceAboveLeftOf(other: Point): Int {
                return calcMaxDiagonalDistance(
                    this distanceAboveOf other,
                    this distanceLeftOf other
                )
            }

            infix fun distanceBelowRightOf(other: Point): Int {
                return calcMaxDiagonalDistance(
                    this distanceBelowOf other,
                    this distanceRightOf other
                )
            }

            infix fun distanceBelowLeftOf(other: Point): Int {
                return calcMaxDiagonalDistance(
                    this distanceBelowOf other,
                    this distanceLeftOf other
                )
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