package day12

import shared.Point


class Day12 {

    companion object {

        fun part1(input: List<String>): Int {
            val graph = parseInput(input)

            val start = graph.find {
                it.char == 'S'
            }!!
            val target = graph.find {
                it.char == 'E'
            }!!

            val distances =  findShortestPath(graph, start)
            return distances[target] ?: -1
        }

        fun part2(input: List<String>): Int {
//            val graph = parseInput(input)
//
//            val start = graph.find {
//                it.char == 'E'
//            }!!
//
//            val distances =  findShortestPath(graph, start)
//
//            return distances.filter {
//                it.key.char == 'a'
//            }.values.minOrNull() ?: -1


//            val graph = parseInput(input)
//
//            val starts = graph.filter {
//                it.char == 'a'
//            }.toList()
//
//
//            val target = graph.find {
//                it.char == 'E'
//            }!!
//            var min = Int.MAX_VALUE
//
//            for (start in starts) {
//                val distances = findShortestPath(graph, start)
//                val newMin = distances[target] ?: Int.MAX_VALUE
//
//                if (newMin < min) {
//                    min = newMin
//                }
//            }
//            return min

            return -1
        }

        private fun findShortestPath(locations: List<Location>, start: Location): Map<Location, Int> {
            val distances = mutableMapOf<Location, Int>()
            val pathStartToAny = mutableMapOf<Location, Location?>()

            // Initialize
            locations.forEach {
                distances[it] = Int.MAX_VALUE
                pathStartToAny[it] = null
            }

            // Set distance from start node to 0
            distances[start] = 0

            val queue = locations.toMutableList()

            while (queue.isNotEmpty()) {
                val u = extractMin(queue, distances)
                queue.remove(u)

                for ((neighbor, distance) in u.neighbors) {
                    // Relaxation
                    if (distances[neighbor]!! > distances[u]!! + distance) {
                        distances[neighbor] = distances[u]!! + distance
                        pathStartToAny[neighbor] = u
                    }
                }
            }

            return distances
        }

        private fun extractMin(queue: List<Location>, distances: Map<Location, Int>): Location {
            var min = queue[0]

            for (location in queue) {
                if (distances[location]!! < distances[min]!!) {
                    min = location
                }
            }
            return min
        }

        data class Location(val char: Char, val point: Point) {
            val neighbors = mutableMapOf<Location, Int>() // Target, distance

            fun addNeighbor(possibleNeighbor: Location) {
                // Only allow movement to smaller or at most 1 higher
                if (possibleNeighbor.elevation() <= elevation() + 1) {
                    neighbors[possibleNeighbor] = 1
                }
            }

            private fun elevation(): Char = when (char) {
                'S' -> 'a'
                'E' -> 'z'
                else -> char
            }
        }

        private fun parseInput(input: List<String>): List<Location> {
            val locationsMap = mutableListOf<List<Location>>()

            // Create nodes
            input.forEachIndexed { y, row ->
                val locationsRow = mutableListOf<Location>()
                row.forEachIndexed { x, char ->
                    locationsRow.add(Location(char, Point(x, y)))
                }
                locationsMap.add(locationsRow)
            }


            // Set neighbors
            val lastYIndex = locationsMap.lastIndex
            val lastXIndex = locationsMap[0].lastIndex

            locationsMap.forEachIndexed { y, row ->
                row.forEachIndexed { x, location ->
                    if (y > 0) {
                        location.addNeighbor(locationsMap[y - 1][x])
                    }
                    if (y < lastYIndex) {
                        location.addNeighbor(locationsMap[y + 1][x])
                    }
                    if (x > 0) {
                        location.addNeighbor(locationsMap[y][x - 1])
                    }
                    if (x < lastXIndex) {
                        location.addNeighbor(locationsMap[y][x + 1])
                    }
                }
            }

            return locationsMap.flatten()
        }
    }
}