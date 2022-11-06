import day01.Day01
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day01Test {

    @ParameterizedTest
    @CsvSource(
        "(()), 0",
        "()(), 0",
        "(((, 3",
        "(()(()(, 3",
        "))(((((, 3",
        "()), -1",
        "))(, -1",
        "))), -3",
        ")())()), -3",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day01.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("day01")
        assertEquals(232, Day01.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "), 1",
        "()()), 5",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day01.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("day01")
        assertEquals(1783, Day01.part2(input))
    }
}