package day02

class Day02 {
    companion object {

        fun part1(input: List<String>): Int {
            return input.map {
                it.split("x").map(String::toInt)
            }.sumOf {
                Box(it[0], it[1], it[2]).wrappingPaperSize()
            }
        }

        fun part2(input: List<String>): Int {
            return input.map {
                it.split("x").map(String::toInt)
            }.sumOf {
                Box(it[0], it[1], it[2]).ribbonSize()
            }
        }
    }
}

data class Box(val width: Int, val length: Int, val height: Int) {
    fun wrappingPaperSize(): Int {
        val areas = listOf(
            length * width,
            width * height,
            height * length
        )

        val smallestSide = areas.minOrNull() ?: 0
        return smallestSide + areas.stream()
            .reduce(0) { left, right -> left + (right * 2) }
    }

    fun ribbonSize(): Int {
        val dimensions = listOf(
            width,
            length,
            height,
        )

        val ribbonWrapSize = dimensions.asSequence()
            .sorted()
            .take(2)
            .sum() * 2

        val ribbonBowSize = dimensions.reduce { acc, value -> acc * value }

        return ribbonWrapSize + ribbonBowSize
    }
}