import day08.Day08
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day08Test {

    private val input = Resource.readFileLines("day08")
    private val testInput = Resource.readFileLines("day08_test")

    
    @Test
    fun `part1 examples`() {
        assertEquals(21, Day08.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(1546, Day08.part1(input))
    }
    
    @Test
    fun `part2 examples`() {
        assertEquals(8, Day08.part2(testInput))
    }

    @Test
    fun `part2 puzzel input`() {
        assertEquals(519064, Day08.part2(input))
    }
}