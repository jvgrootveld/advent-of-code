import day03.Day03
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day03Test {

    @Test
    fun `part1 examples`() {
        val input = Resource.readFileLines("day03_test")
        assertEquals(157, Day03.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day03")
        assertEquals(8202, Day03.part1(input))
    }

    @Test
    fun `part2 examples`() {
        val input = Resource.readFileLines("day03_test")
        assertEquals(70, Day03.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day03")
        assertEquals(2864, Day03.part2(input))
    }
}