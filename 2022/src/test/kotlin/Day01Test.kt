import day01.Day01
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day01Test {

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day01.part1(listOf(input)))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day01")
        assertEquals(0, Day01.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day01.part2(listOf(input)))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day01")
        assertEquals(0, Day01.part2(input))
    }
}