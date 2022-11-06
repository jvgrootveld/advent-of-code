import day02.Day02
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day02Test {

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day02.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("day02")
        assertEquals(0, Day02.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day02.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("day02")
        assertEquals(0, Day02.part2(input))
    }
}