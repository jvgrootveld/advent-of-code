package day03

class Day03 {
    companion object {

        fun part1(input: String): Int {
            val grid = Grid()
            val santa = Santa(grid)

            input.forEach(santa::walk)

            return grid.countHousesWithPresents()
        }

        fun part2(input: String): Int {
            val grid = Grid()
            val santa = Santa(grid)
            val roboSanta = Santa(grid)

            input.chunked(2).forEach {
                santa.walk(it[0])
                if (it.length == 2) {
                    roboSanta.walk(it[1])
                }
            }

            return grid.countHousesWithPresents()
        }
    }
}

sealed class Direction {
    companion object {
        const val UP = '^'
        const val DOWN = 'v'
        const val LEFT = '<'
        const val RIGHT = '>'
    }
}

data class Point(val x: Int, val y: Int) {

    fun newPointWithDirection(directionSign: Char): Point = when (directionSign) {
        Direction.UP -> Point(x, y + 1)
        Direction.DOWN -> Point(x, y - 1)
        Direction.LEFT -> Point(x - 1, y)
        Direction.RIGHT -> Point(x + 1, y)
        else -> throw IllegalArgumentException("Direction sign '$directionSign' not supported")
    }
}

class Santa(private val grid: Grid) {
    private var currentPosition = Point(0, 0)

    init {
        grid.givePresent(currentPosition)
    }

    /**
     * Walks on the grid from [currentPosition] and give a present.
     */
    fun walk(directionSign: Char) {
        currentPosition = currentPosition.newPointWithDirection(directionSign)
        grid.givePresent(currentPosition)
    }
}

class Grid {
    private val grid = HashMap<Point, Int>()

    /**
     * Increase presents on given point on grid.
     */
    fun givePresent(position: Point) {
        grid.getOrDefault(position, 0).let {currentPresents ->
            grid[position] = currentPresents + 1
        }
    }

    /**
     * Return houses with presents.
     * Note: Because Santa does not take away presents, it's the same as the unique houses visited.
     */
    fun countHousesWithPresents() = grid.size
}