import day06.Day06
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


internal class Day06Test {

    @ParameterizedTest
    @CsvSource(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 7",
        "bvwbjplbgvbhsrlpgdmjqwftvncz, 5",
        "nppdvjthqldpwncqszvftbrmjlhg, 6",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 10",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 11",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day06.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("day06")
        assertEquals(1080, Day06.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 19",
        "bvwbjplbgvbhsrlpgdmjqwftvncz, 23",
        "nppdvjthqldpwncqszvftbrmjlhg, 23",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 29",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 26",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day06.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("day06")
        assertEquals(3645, Day06.part2(input))
    }
}