import day04.Day04
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class Day04Test {

    @Test
    fun `part1 examples`() {
        val input = Resource.readFileLines("day04_test")
        assertEquals(2, Day04.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day04")
        assertEquals(433, Day04.part1(input))
    }

    @Test
    fun `part2 examples`() {
        val input = Resource.readFileLines("day04_test")
        assertEquals(4, Day04.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day04")
        assertEquals(852, Day04.part2(input))
    }
}