import day12.Day12
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day12Test {

    private val input = Resource.readFileLines("day12")
    private val testInput = Resource.readFileLines("day12_test")

    @Test
    fun `part1 example`() {
        assertEquals(31, Day12.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(468, Day12.part1(input))
    }

    @Test
    fun `part2 example`() {
        assertEquals(29, Day12.part2(testInput))
    }

    @Test
    fun `part2 puzzel input`() {
        assertEquals(0, Day12.part2(input))
    }
}