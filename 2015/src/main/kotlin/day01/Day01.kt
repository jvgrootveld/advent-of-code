package day01

class Day01 {
    companion object {

        private const val FLOOR_UP_CHAR = '('

        fun part1(input: String): Int {

            val floor = input.let {
                val upCount = it.count { char -> char == FLOOR_UP_CHAR }
                val downCount = (it.length - upCount)
                upCount - downCount
            }

            return floor
        }

        fun part2(input: String): Int {

            var position = 1
            var currentFloor = 0
            for (char in input) {
                when (char) {
                    FLOOR_UP_CHAR -> currentFloor++
                    else -> currentFloor--
                }

                if (currentFloor < 0) {
                    break;
                }

                position++
            }

            return position;
        }
    }
}