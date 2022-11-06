import day02.Day02
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day02Test {

    @ParameterizedTest
    @CsvSource(
        "2x3x4, 58",
        "1x1x10, 43",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day02.part1(listOf(input)))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day02")
        assertEquals(1586300, Day02.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "2x3x4, 34",
        "1x1x10, 14",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day02.part2(listOf(input)))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day02")
        assertEquals(3737498, Day02.part2(input))
    }
}