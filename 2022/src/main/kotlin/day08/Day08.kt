package day08

class Day08 {

    companion object {

        fun part1(input: List<String>): Int {
            val pointsAlreadyVisited = mutableListOf<Point>()
            val grid = Grid.of(input)
            val size = grid.size

            var totalVisibleTrees = (size + size - 2) * 2 // Border

            for (tree in grid.innerBoundary()) {

                val currentHeight = grid[tree]

                val orientations = listOf(
                    sequenceToLeft(tree),
                    sequenceToRight(tree, size),
                    sequenceToTop(tree),
                    sequenceToBottom(tree, size)
                )

                for (orientation in orientations) {
                    val isVisible = isVisibleFrom(grid, currentHeight, orientation)

                    if (isVisible) {
                        if (!pointsAlreadyVisited.contains(tree)) {
                            pointsAlreadyVisited.add(tree)
                            totalVisibleTrees++
                        }
                    }
                }
            }

            //grid.print(pointsAlreadyVisited)

            return totalVisibleTrees;
        }

        fun part2(input: List<String>): Int {
            val grid = Grid.of(input)
            val size = grid.size

            val scenicScores = mutableListOf<Int>()

            for (tree in grid.innerBoundary()) {

                val currentHeight = grid[tree]

                val orientations = listOf(
                    sequenceToLeft(tree),
                    sequenceToRight(tree, size),
                    sequenceToTop(tree),
                    sequenceToBottom(tree, size)
                )

                var currentScenicScore = 1
                for (orientation in orientations) {
                    currentScenicScore *= calculateScenicScore(grid, currentHeight, orientation)
                }

                scenicScores.add(currentScenicScore)
            }

            return scenicScores.maxOrNull() ?: -1;
        }

        private fun isVisibleFrom(grid: Grid, currentHeight: Int, sequence: Sequence<Point>): Boolean {
            for(point in sequence) {
                if (grid[point] >= currentHeight) {
                    return false;
                }
            }

            return true
        }

        private fun calculateScenicScore(grid: Grid, currentHeight: Int, sequence: Sequence<Point>): Int {
            var lineScenicScore = 0

            for(point in sequence) {
                lineScenicScore++
                if (grid[point] >= currentHeight) {
                    break
                }
            }

            return lineScenicScore
        }

        private fun sequenceToLeft(tree: Point) = sequence {
            for (fromLeft in tree.x - 1 downTo 0) {
                yield(Point(fromLeft, tree.y))
            }
        }

        private fun sequenceToRight(tree: Point, size: Int) = sequence {
            for (fromRight in tree.x + 1 until size) {
                yield(Point(fromRight, tree.y))
            }
        }

        private fun sequenceToTop(tree: Point) = sequence {
            for (fromTop in tree.y - 1 downTo 0) {
                yield(Point(tree.x, fromTop))
            }
        }

        private fun sequenceToBottom(tree: Point, size: Int) = sequence {
            for (fromBottom in tree.y + 1 until size) {
                yield(Point(tree.x, fromBottom))
            }
        }

        data class Point(val x: Int, val y: Int)

        class Grid(private val gridOfTreeHeights: Array<Array<Int>>) {

            val size: Int
                get() = gridOfTreeHeights.size

            companion object {
                fun of(input: List<String>): Grid {
                    var grid = arrayOf<Array<Int>>()

                    val numberCharOffset = '0'.code

                    input.forEach { rowValues ->
                        var row = arrayOf<Int>()
                        rowValues.forEach { height ->
                            row += height.code - numberCharOffset
                        }

                        grid += row
                    }

                    return Grid(grid)
                }
            }

            operator fun get(point: Point): Int {
                return gridOfTreeHeights[point.y][point.x]
            }

            operator fun get(y: Int): Array<Int> {
                return gridOfTreeHeights[y]
            }

            fun innerBoundary() = sequence {
                val size = gridOfTreeHeights.size
                for (y in 1 until size - 1) {
                    for (x in 1 until size - 1) {
                        yield(Point(x, y))
                    }
                }
            }

            fun print(pointsAlreadyVisited: MutableList<Point>) {
                val size = gridOfTreeHeights.size
                for (y in 0 until size) {
                    for (x in 0 until size) {
                        val tree = Point(x, y)

                        if (pointsAlreadyVisited.contains(tree) ||
                            tree.x == 0 || tree.x == size -1 || tree.y == 0 || tree.y == size -1 // Boundary
                            ) {
                            print("[${this[tree]}]")
                        } else {
                            print(" ${this[tree]} ")
                        }
                    }
                    println()
                }

            }
        }
    }
}