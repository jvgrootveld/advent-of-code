import day08.Day08
import day08.Day08.Companion.LineResult
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day08Test {

    @Test
    fun `part1 examples`() {
        val input = Resource.readFileAsBytes("day08_test")
        val result = Day08.part1(input)
        assertLineResult(result.lineResults[0], 2, 0)  // ""          =  2 code, 0 memory
        assertLineResult(result.lineResults[1], 5, 3)  // "abc"       =  5 code, 3 memory
        assertLineResult(result.lineResults[2], 10, 7) // "aaa\"aaa"  = 10 code, 7 memory
        assertLineResult(result.lineResults[3], 6, 1)  // "\x27"      =  6 code, 1 memory
    }

    private fun assertLineResult(lineResult: LineResult, codeCount: Int, memoryCount: Int) {
        assertEquals(codeCount, lineResult.codeCount)
        assertEquals(memoryCount, lineResult.memoryCount)
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileAsBytes("day08")
        assertEquals(1333, Day08.part1(input).countTotal())
    }

    @ParameterizedTest
    @CsvSource(
        "\"\", 4",             // "" encodes to "\"\""                  =  6 -  2 = 4
        "\"abc\", 4",         // "abc" encodes to "\"abc\""             =  9 -  5 = 4
        "\"aaa\\\"aaa\", 6",  // "aaa\"aaa" encodes to "\"aaa\\\"aaa\"" = 16 - 10 = 6
        "\"\\x27\", 5",       // "\x27" encodes to "\"\\x27\""          = 11 -  6 = 5
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day08.part2(input.toByteArray()))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileAsBytes("day08")
        assertEquals(2046, Day08.part2(input))
    }
}