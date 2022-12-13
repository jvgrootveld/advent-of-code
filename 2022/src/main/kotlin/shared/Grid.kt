package shared

import day09.Day09

enum class Direction(val x: Int, val y: Int) {
    UP(0, 1),
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0),
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1);

    companion object {
        val xyDirections = listOf(UP, RIGHT, DOWN, LEFT)
    }
}

data class Point(var x: Int = 0, var y: Int = 0) {
    fun newPointWithDirection(direction: Direction) = Point(x + direction.x, y + direction.y)

    fun applyDirection(direction: Day09.Companion.Direction) {
        x += direction.x
        y += direction.y
    }
}