package day11

import shared.extractLongs
import java.lang.RuntimeException
import java.util.LinkedList

class Day11 {

    companion object {

        fun part1(input: String): Long {
            val monkeys = parseInput(input).associateBy(Monkey::id)
            return startGame(monkeys, 20) { it / 3 }
        }

        fun part2(input: String): Long {
            val monkeys = parseInput(input).associateBy(Monkey::id)

            val commonDenominator = monkeys.values
                .map(Monkey::divisibleValue)
                .reduce { acc, v -> acc * v }

            return startGame(monkeys, 10_000) { it % commonDenominator }
        }

        private fun startGame(monkeys: Map<Int, Monkey>, rounds: Int, reliefOperation: ReliefOperation): Long {
            for (round in 1..rounds) {
                monkeys.forEach { (_, monkey) ->
                    monkey.items.forEach { item ->
                        val newWorryLevel = reliefOperation(monkey.inspectItem(item))
                        val newMonkeyId = monkey.chooseTargetMonkey(newWorryLevel)

                        monkeys[newMonkeyId]?.items?.add(newWorryLevel)
                    }
                    monkey.items.clear()
                }
            }

            val (m1, m2) = monkeys.values
                .sortedByDescending(Monkey::totalItemsInspected)
                .take(2)

            return m1.totalItemsInspected * m2.totalItemsInspected
        }

        data class Monkey(
            val id: Int,
            val inspectOperation: MonkeyOperation,
            val chooseTargetMonkey: MonkeyTest,
            val items: MutableList<Long>,
            val divisibleValue: Int
        ) {
            var totalItemsInspected = 0L
                private set

            fun inspectItem(itemWorryLevel: Long): Long {
                totalItemsInspected++
                return inspectOperation(itemWorryLevel)
            }
        }

        /**
         * Monkey 0:
         *   Starting items: 79, 98
         *   Operation: new = old * 19
         *   Test: divisible by 23
         *     If true: throw to monkey 2
         *     If false: throw to monkey 3
         */
        private fun parseInput(input: String): LinkedList<Monkey> {
            val monkeys = LinkedList<Monkey>()

            input.split("\n\n").mapIndexed { i, value ->
                val lines = value.split("\n").map(String::trimIndent)

                // Parse: Starting items: 79, 98
                val items = lines[1].extractLongs()
                val operation = parseOperation(lines[2])
                val test = parseTest(lines[3], lines[4], lines[5])
                val divisibleValue = lines[3].split(" ").last().toInt()

                Monkey(i, operation, test, items.toMutableList(), divisibleValue)
            }.forEach(monkeys::add)

            return monkeys
        }

        // Parse: Operation: new = old * 19
        private fun parseOperation(input: String): MonkeyOperation {
            val parts = input.split(" ")
            val value = parts.last()

            return when (val sign = parts[parts.lastIndex - 1]) {
                "+" -> createPlusOperation(value)
                "-" -> createMinOperation(value)
                "*" -> createMultiplyOperation(value)
                "/" -> createDivideOperation(value)
                else -> throw RuntimeException("Unsupported operation sign: '$sign'")
            }
        }

        // Parse:
        // Test: divisible by 2
        // If true: throw to monkey 5
        // If false: throw to monkey 2
        private fun parseTest(inputTest: String, inputTrue: String, inputFalse: String): MonkeyTest {
            val divisibleValue = inputTest.split(" ").last().toInt()
            val trueTargetMonkey = inputTrue.split(" ").last().toInt()
            val falseTargetMonkey = inputFalse.split(" ").last().toInt()

            return {
                if (it % divisibleValue == 0L) trueTargetMonkey else falseTargetMonkey
            }
        }

        private fun createPlusOperation(value: String): MonkeyOperation = {
            if (value == "old") it + it else value.toLong() + it
        }

        private fun createMinOperation(value: String): MonkeyOperation = {
            if (value == "old") it - it else value.toLong() - it
        }

        private fun createMultiplyOperation(value: String): MonkeyOperation = {
            if (value == "old") it * it else value.toLong() * it
        }

        private fun createDivideOperation(value: String): MonkeyOperation = {
            if (value == "old") it / it else value.toLong() / it
        }
    }
}

typealias MonkeyOperation = (value: Long) -> Long
typealias MonkeyTest = (value: Long) -> Int
typealias ReliefOperation = (value: Long) -> Long
