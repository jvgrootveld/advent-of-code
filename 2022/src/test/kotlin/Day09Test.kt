import day09.Day09
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class Day09Test {

    private val input = Resource.readFileLines("day09")
    private val testInput = Resource.readFileLines("day09_test")
    private val testInput2 = Resource.readFileLines("day09_test_2")

    @Test
    fun `part1 examples`() {
        assertEquals(13, Day09.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(6522, Day09.part1(input))
    }

    @Test
    fun `part2 example 1`() {
        assertEquals(1, Day09.part2(testInput))
    }

    @Test
    fun `part2 example 2`() {
        assertEquals(36, Day09.part2(testInput2))
    }

    @Test
    fun `part2 puzzel input`() {
        assertEquals(2717, Day09.part2(input))
    }
}