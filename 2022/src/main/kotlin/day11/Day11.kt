package day11

import java.lang.RuntimeException
import java.util.LinkedList

class Day11 {

    companion object {

        private val MATCH_NUMBERS = Regex("[0-9]+")

        fun part1(input: String): Long {
            val monkeys = parseInput(input).associateBy(Monkey::id)

            for (round in 1..20) {
                monkeys.forEach { (_, monkey) ->
                    monkey.items.forEach { item ->
                        item.worryLevel = monkey.inspectItem(item) / 3
                        val newMonkeyId = monkey.chooseTargetMonkey(item.worryLevel)

                        monkeys[newMonkeyId]?.items?.add(item)
                    }
                    monkey.items.clear()
                }
            }

            val (m1, m2) = monkeys.values
                .sortedByDescending(Monkey::totalItemsInspected)
                .take(2)

            return m1.totalItemsInspected * m2.totalItemsInspected
        }

        fun part2(input: String): Long {
            val monkeys = parseInput(input).associateBy(Monkey::id)

            for (round in 1..10_000) {
                monkeys.forEach { (_, monkey) ->
                    monkey.items.forEach { item ->
                        item.worryLevel = monkey.inspectItem(item)
                        val newMonkeyId = monkey.chooseTargetMonkey(item.worryLevel)

                        monkeys[newMonkeyId]?.items?.add(item)
                    }
                    monkey.items.clear()
                }
            }

            val (m1, m2) = monkeys.values
                .sortedByDescending(Monkey::totalItemsInspected)
                .take(2)

            // After 10000 rounds, the two most active monkeys inspected
            // items 52166 and 52013 times.
            // Multiplying these together, the level of monkey business in this situation
            // is now 2713310158.
            return m1.totalItemsInspected * m2.totalItemsInspected
        }

        data class Item(val id: Int, var worryLevel: Long)

        data class Monkey(
            val id: Int,
            val inspectOperation: MonkeyOperation,
            val chooseTargetMonkey: MonkeyTest,
            val items: LinkedList<Item>
        ) {
            var totalItemsInspected = 0L
                private set

            fun inspectItem(item: Item): Long {
                totalItemsInspected++
                return inspectOperation(item.worryLevel)
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
        fun parseInput(input: String): LinkedList<Monkey> {
            val monkeys = LinkedList<Monkey>()
            val itemIndexGenerator = generateSequence(0) { it + 1 }.iterator()

            input.split("\n\n").mapIndexed { i, value ->
                val lines = value.split("\n").map(String::trimIndent)

                val items = parseItems(lines[1], itemIndexGenerator)
                val operation = parseOperation(lines[2])
                val test = parseTest(lines[3], lines[4], lines[5])

                Monkey(i, operation, test, LinkedList<Item>(items))
            }.forEach(monkeys::add)

            return monkeys
        }

        // Parse: Starting items: 79, 98
        private fun parseItems(input: String, itemIndexGenerator: Iterator<Int>): List<Item> {
            return MATCH_NUMBERS.findAll(input).map {
                Item(itemIndexGenerator.next(), it.value.toLong())
            }.toList()
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