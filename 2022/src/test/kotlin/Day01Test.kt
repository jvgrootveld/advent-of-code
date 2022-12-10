import day01.Day01
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day01Test {

    @Test
    fun `part1 example`() {
        val input = Resource.readFileLines("day01_test")
        assertEquals(24000, Day01.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day01")
        assertEquals(71502, Day01.part1(input))
    }

    @Test
    fun `part2 example`() {
        val input = Resource.readFileLines("day01_test")
        assertEquals(45000, Day01.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day01")
        assertEquals(208191, Day01.part2(input))
    }
}