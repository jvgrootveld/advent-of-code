import day02.Day02
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day02Test {

    @Test
    fun `part1 example`() {
        val input = Resource.readFileLines("day02_test")
        assertEquals(15, Day02.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day02")
        assertEquals(8392, Day02.part1(input))
    }

    @Test
    fun `part2 examples`() {
        val input = Resource.readFileLines("day02_test")
        assertEquals(12, Day02.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day02")
        assertEquals(10116, Day02.part2(input))
    }
}