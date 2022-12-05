import day05.Day05
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day05Test {

    @Test
    fun `part1 example`() {
        val input = Resource.readFile("day05_test")
        assertEquals("CMZ", Day05.day05(input, Day05.Companion.CraneType.CRATE_MOVER_9000))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("day05")
        assertEquals("FCVRLMVQP", Day05.day05(input, Day05.Companion.CraneType.CRATE_MOVER_9000))
    }

    @Test
    fun `part2 example`() {
        val input = Resource.readFile("day05_test")
        assertEquals("MCD", Day05.day05(input, Day05.Companion.CraneType.CRATE_MOVER_9001))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("day05")
        assertEquals("RWLWGJGFD", Day05.day05(input, Day05.Companion.CraneType.CRATE_MOVER_9001))
    }
}