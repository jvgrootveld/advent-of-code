import day10.Day10
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class Day10Test {

    private val input = Resource.readFileLines("day10")
    private val testInput = Resource.readFileLines("day10_test")

    @Test
    fun `part1 example`() {
        assertEquals(13140, Day10.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(10760, Day10.part1(input))
    }

    @Test
    fun `part2 example`() {
        val expectedResult = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        Day10.part2(testInput).also {
            print(it)
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `part2 puzzel input`() {
        val expectedResult = """
            ####.###...##..###..#..#.####..##..#..#.
            #....#..#.#..#.#..#.#..#.#....#..#.#..#.
            ###..#..#.#....#..#.####.###..#....####.
            #....###..#.##.###..#..#.#....#.##.#..#.
            #....#....#..#.#....#..#.#....#..#.#..#.
            #....#.....###.#....#..#.#.....###.#..#.
        """.trimIndent()

        Day10.part2(input).also {
            print(it)
            assertEquals(expectedResult, it)
        }
    }
}