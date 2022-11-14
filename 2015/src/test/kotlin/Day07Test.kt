import day07.Day07
import day07.Day07.Companion.Wire
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day07Test {

    private val part1Example = """
        123 -> x
        456 -> y
        x AND y -> d
        x OR y -> e
        x LSHIFT 2 -> f
        y RSHIFT 2 -> g
        NOT x -> h
        NOT y -> i
    """.trimIndent()

    @Test
    fun `part1 examples`() {
        Day07.part1(part1Example.lines()).let {
            assertEquals(8, it.size)
            assertInWireStates(it, "d", 72u)
            assertInWireStates(it, "e", 507u)
            assertInWireStates(it, "f", 492u)
            assertInWireStates(it, "g", 114u)
            assertInWireStates(it, "h", 65412u)
            assertInWireStates(it, "i", 65079u)
            assertInWireStates(it, "x", 123u)
            assertInWireStates(it, "y", 456u)
        }
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFileLines("day07")
        val result = Day07.part1(input)
        assertInWireStates(result, "a", 0u)
    }

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day07.part2(listOf(input)))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFileLines("day07")
        assertEquals(0, Day07.part2(input))
    }

    private fun assertInWireStates(wires: Map<String, Wire>, wireId: String, expectedResult: UShort) {
        val wire = wires[wireId] ?: fail("Wire '$wireId' not found in map")
        assertEquals(expectedResult, wire.signal)
    }
}