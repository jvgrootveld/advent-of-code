import day04.Day04
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day04Test {

    @ParameterizedTest
    @CsvSource(
        "abcdef, 609043",
        "pqrstuv, 1048970",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day04.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = "bgvyzdsv"
        assertEquals(254575, Day04.part1(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = "bgvyzdsv"
        assertEquals(1038736, Day04.part2(input))
    }
}