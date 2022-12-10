import day07.Day07
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class Day07Test {

    private val input = Resource.readFileLines("day07")
    private val testInput = Resource.readFileLines("day07_test")


    @Test
    fun `part1 examples`() {
        assertEquals(95437, Day07.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(1491614, Day07.part1(input))
    }

    @Test
    fun `part2 examples`() {
        assertEquals(24933642, Day07.part2(testInput))
    }

    @Test
    fun `part2 puzzel input`() {
        assertEquals(6400111, Day07.part2(input))
    }
}