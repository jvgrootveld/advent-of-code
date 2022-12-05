package day05

import java.util.*

class Day05 {

    companion object {

        private val MATCH_NUMBERS = Regex("[0-9]+")

        enum class CraneType {
            CRATE_MOVER_9000,
            CRATE_MOVER_9001
        }

        fun day05(input: String, craneType: CraneType): String {
            val inputParts = input.split("\n\n")
            val stacks = parseStacks(inputParts[0])
            val instructions = parseInstructions(inputParts[1])

            instructions.forEach { instruction ->
                val pick = mutableListOf<Char>()

                repeat(instruction.move) {
                    stacks[instruction.fromStack]?.pop()?.also {
                        pick.add(it)
                    }
                }

                stacks[instruction.toStack]?.addAll(
                    if (craneType == CraneType.CRATE_MOVER_9000) {
                        pick
                    } else {
                        pick.reversed()
                    }
                )
            }

            return getLastValues(stacks.values)
        }

        // Parse Stacks format:
        //     [D]
        // [N] [C]
        // [Z] [M] [P]
        //  1   2   3
        private fun parseStacks(input: String): HashMap<Int, Stack<Char>> {
            val stacks = HashMap<Int, Stack<Char>>()

            val lines = input.split("\n").reversed().toMutableList().drop(1)

            lines.forEach {
                var stackI = 0
                var i = 1
                while (i < it.length) {
                    if (!it[i].isWhitespace()) {
                        stacks
                            .getOrPut(stackI) { Stack<Char>() }
                            .push(it[i])
                    }
                    i += 4
                    stackI++
                }
            }

            return stacks
        }

        // Parse format 'move 6 from 9 to 3'
        data class Instruction(val move: Int, val fromStack: Int, val toStack: Int)

        private fun parseInstructions(input: String): List<Instruction> {
            return input.split("\n").map {
                val (move, from, to) = MATCH_NUMBERS.findAll(it)
                    .map(MatchResult::value)
                    .map(String::toInt)
                    .toList()

                Instruction(move, from - 1, to - 1)
            }
        }

        private fun getLastValues(values: Collection<Stack<Char>>): String {
            return values.mapNotNull {
                it.pop()
            }.joinToString("")
        }
    }
}