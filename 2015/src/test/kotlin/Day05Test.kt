import day05.Day05
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day05Test {

    @ParameterizedTest
    @CsvSource(
        "ugknbfddgicrmopn, 1",
        "aaa, 1",
        "jchzalrnumimnmhp, 0",
        "haegwjzuvuyypxyu, 0",
        "dvszwmarrgswjxmb, 0",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day05.part1(listOf(input)))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day05")
        assertEquals(255, Day05.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "qjhvhtzxzqqjkmpb, 1",
        "xxyxx, 1",
        "uurcxstgmygtbstg, 0",
        "ieodomkazucvgmuy, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day05.part2(listOf(input)))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day05")
        assertEquals(55, Day05.part2(input))
    }
}