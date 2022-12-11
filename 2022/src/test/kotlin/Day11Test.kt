import day11.Day11
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day11Test {

    private val input = Resource.readFile("day11")
    private val testInput = Resource.readFile("day11_test")
    @Test
    fun `part1 example`() {
        assertEquals(10605, Day11.part1(testInput))
    }
    @Test
    fun `part1 puzzel input`() {
        assertEquals(117624, Day11.part1(input))
    }
    @Test
    fun `part2 example`() {
        assertEquals(2713310158, Day11.part2(testInput))
    }
    @Test
    fun `part2 puzzel input`() {
        assertEquals(16792940265, Day11.part2(input))
    }
}