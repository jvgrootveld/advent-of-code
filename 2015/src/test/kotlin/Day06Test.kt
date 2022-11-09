import day06.Day06
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class Day06Test {

    companion object {
        @JvmStatic
        fun part1ExamplesTestCases(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", 1000000),
                Arguments.of("toggle 0,0 through 999,0", 1000),
                Arguments.of("turn off 499,499 through 500,500", 0),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("Day06Test#part1ExamplesTestCases")
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day06.part1(listOf(input)))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day06")
        assertEquals(377891, Day06.part1(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day06")
        assertEquals(14110788, Day06.part2(input))
    }
}