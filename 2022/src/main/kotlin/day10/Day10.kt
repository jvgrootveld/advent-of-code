package day10

class Day10 {

    companion object {

        fun part1(input: List<String>): Int {
            val commands = parseInput(input)

            var register = 1
            var signalStrengths = 0

            commands.forEachIndexed { i, command ->
                val cycle = i + 1
                if (((cycle - 20) % 40 == 0)) {
                    signalStrengths += cycle * register
                }

                register += command.amount
            }

            return signalStrengths
        }

        fun part2(input: List<String>): String {
            val commands = parseInput(input)
            val pixels = mutableListOf<Pixel>()
            val lineSize = 40

            var register = 1

            commands.forEachIndexed { i, command ->
                val pixelLocationInLine = i % lineSize

                when (pixelLocationInLine isAround register) {
                    true -> pixels.add(Pixel.LIT)
                    false -> pixels.add(Pixel.DARK)
                }

                register += command.amount
            }

            return drawPixels(pixels, lineSize)
        }

        enum class Pixel(val sign: Char) {
            DARK('.'),
            LIT('#')
        }

        /**
         * Check if a number is around another number like:
         *   1 isAround 2 is true because 5 exists in [1,2,3]
         *   4 isAround 2 is false because 5 does not exist in [1,2,3]
         */
        private infix fun Int.isAround(value: Int): Boolean {
            return (this >= value - 1 && this <= value + 1)
        }

        private fun drawPixels(pixels: List<Pixel>, lineSize: Int): String {
            val screen = StringBuilder()
            pixels.forEachIndexed { i, pixel ->
                if (i > 0 && i % lineSize == 0) {
                    screen.append('\n')
                }
                screen.append(pixel.sign)
            }
            return screen.toString()
        }

        data class Command(val amount: Int = 0)

        private fun parseInput(input: List<String>): List<Command> {
            val commands = mutableListOf<Command>()

            for (line in input) {
                val parts = line.split(" ")

                when (parts[0]) {
                    "noop" -> commands.add(Command())
                    "addx" -> {
                        commands.add(Command()) // Add extra to simulate addx command to take two cycles
                        commands.add(Command(parts[1].toInt()))
                    }
                }
            }

            return commands
        }
    }
}