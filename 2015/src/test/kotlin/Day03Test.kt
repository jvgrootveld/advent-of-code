import day03.Day03
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day03Test {

    @ParameterizedTest
    @CsvSource(
        ">, 2",
        "^>v<, 4",
        "^v^v^v^v^v, 2",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day03.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("day03")
        assertEquals(2565, Day03.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "^v, 3",
        "^>v<, 3",
        "^v^v^v^v^v, 11",
        "^>v<^, 3", // Uneven test
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day03.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("day03")
        assertEquals(2639, Day03.part2(input))
    }
}